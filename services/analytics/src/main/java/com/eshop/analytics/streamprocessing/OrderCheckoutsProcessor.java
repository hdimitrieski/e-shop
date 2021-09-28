package com.eshop.analytics.streamprocessing;

import com.eshop.analytics.model.Product;
import com.eshop.analytics.model.ProductSellCount;
import com.eshop.analytics.model.TopFiveProducts;
import com.eshop.analytics.streamprocessing.events.UserCheckoutAcceptedIntegrationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.Grouped;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Materialized;
import org.apache.kafka.streams.state.KeyValueStore;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.HashMap;
import java.util.UUID;
import java.util.function.Consumer;

import static com.eshop.analytics.common.Constants.*;

@RequiredArgsConstructor
@Configuration
public class OrderCheckoutsProcessor {
  private final ObjectMapper mapper;

  @Bean
  public Consumer<KStream<String, UserCheckoutAcceptedIntegrationEvent>> ordercheckouts() {
    return (checkoutEvents) -> {
      final var eventSerde = new JsonSerde<>(UserCheckoutAcceptedIntegrationEvent.class, mapper);
      final var keyProductSerde = new JsonSerde<>(Product.class, mapper);
      keyProductSerde.configure(new HashMap<>(), true);
      final var productSerde = new JsonSerde<>(Product.class, mapper);
      final var productSellCountSerde = new JsonSerde<>(ProductSellCount.class, mapper);
      final var topFiveSerde = new TopFiveSerde();

      final KStream<String, UserCheckoutAcceptedIntegrationEvent> checkoutsByUserId =
          checkoutEvents.map((key, value) -> KeyValue.pair(value.getUserId(), value));

      // create a state store to track order checkouts by user
      checkoutsByUserId.groupByKey(Grouped.with(Serdes.String(), eventSerde))
          .count(Materialized.as(CHECKOUTS_BY_USER_STORE));

      final KStream<UUID, Product> sellsByProductId = checkoutEvents
          .flatMapValues((value) -> value.getBasket().getItems())
          .map((key, value) -> KeyValue.pair(value.getProductId(), new Product(value.getProductId())));

      // create a state store to track product sell counts
      final KTable<Product, Long> productSellCounts = sellsByProductId
          .groupBy((productId, product) -> product, Grouped.with(keyProductSerde, productSerde))
          .count(Materialized.<Product, Long, KeyValueStore<Bytes, byte[]>>as(PRODUCT_SELL_COUNT_STORE)
              .withKeySerde(productSerde)
              .withValueSerde(Serdes.Long())
          );

      // Compute the top five products. The results of this computation will continuously update the state
      // store "top-five-products", and this state store can then be queried interactively via a REST API
      // for the latest top products.
      productSellCounts
          .groupBy((product, sells) ->
                  KeyValue.pair(TOP_FIVE_PRODUCTS_STORE_KEY, new ProductSellCount(product.getId(), sells)),
              Grouped.with(Serdes.String(), productSellCountSerde))
          .aggregate(
              TopFiveProducts::new,
              (key, productSellCount, aggregate) -> {
                aggregate.add(productSellCount);
                return aggregate;
              },
              (key, productSellCount, aggregate) -> {
                aggregate.remove(productSellCount);
                return aggregate;
              },
              Materialized.<String, TopFiveProducts, KeyValueStore<Bytes, byte[]>>as(TOP_FIVE_PRODUCTS_STORE)
                  .withKeySerde(Serdes.String())
                  .withValueSerde(topFiveSerde)
          );
    };
  }

}
