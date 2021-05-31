package com.eshop.analytics.streamprocessing;

import com.eshop.analytics.model.Order;
import com.eshop.analytics.streamprocessing.events.OrderStatusChangedToCancelledIntegrationEvent;
import com.eshop.analytics.streamprocessing.events.OrderStatusChangedToPaidIntegrationEvent;
import com.eshop.analytics.streamprocessing.events.OrderStatusChangedToSubmittedIntegrationEvent;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.common.utils.Bytes;
import org.apache.kafka.streams.KeyValue;
import org.apache.kafka.streams.kstream.*;
import org.apache.kafka.streams.state.KeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.support.serializer.JsonSerde;

import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Function;

import static com.eshop.analytics.common.Constants.*;

@RequiredArgsConstructor
@Configuration
public class OrderStatisticsProcessor {
  private static final Logger logger = LoggerFactory.getLogger(OrderStatisticsProcessor.class);

  private final ObjectMapper mapper;

  @Bean
  public BiConsumer<
      KStream<String, OrderStatusChangedToSubmittedIntegrationEvent>,
      KStream<String, OrderStatusChangedToCancelledIntegrationEvent>> orderstatistics() {
    return (submittedOrders, cancelledOrders) -> {
      final var submittedOrdersById = submittedOrders
          .map((key, event) -> KeyValue.pair(event.getOrderId(), event.getOrderId()));
      final var cancelledOrdersById = cancelledOrders
          .map((key, event) -> KeyValue.pair(event.getOrderId(), event.getOrderId()));

      submittedOrdersById.merge(cancelledOrdersById).map(KeyValue::pair)
          .foreach((key, orderId) ->
              logger.info("New event in Order Statistics processor. Order id {}", orderId));
    };
  }

  /**
   * Store all submitted orders in a table.
   */
  @Bean
  public Function<KStream<String, OrderStatusChangedToSubmittedIntegrationEvent>, KStream<Long, Order>> allsubmittedorders() {
    return (submittedOrderEventsInput) -> {
      final var orderSerde = new JsonSerde<>(Order.class, mapper);

      final var submittedOrders = submittedOrderEventsInput
          .map((readOnlyKey, event) -> KeyValue.pair(
              event.getOrderId(),
              new Order(event.getOrderId(), event.getTotalPrice(), event.getOrderItems())
          ));

      return submittedOrders
          .toTable(Materialized.<Long, Order, KeyValueStore<Bytes, byte[]>>as(SUBMITTED_ORDERS_STORE)
              .withKeySerde(Serdes.Long())
              .withValueSerde(orderSerde))
          .toStream();
    };
  }

  /**
   * Store all paid orders in a table.
   */
  @Bean
  public BiFunction<
      KStream<String, OrderStatusChangedToPaidIntegrationEvent>,
      KTable<Long, Order>,
      KStream<Long, Order>
      > allpaidorders() {
    return (paidOrdersInput, allSubmittedOrders) -> {
      final var eventSerde = new JsonSerde<>(OrderStatusChangedToPaidIntegrationEvent.class, mapper);
      final var orderSerde = new JsonSerde<>(Order.class, mapper);

      final var paidOrderEventsByOrderId = paidOrdersInput
          .map(((key, event) -> KeyValue.pair(event.getOrderId(), event)));

      final var paidOrders = paidOrderEventsByOrderId.leftJoin(
          allSubmittedOrders,
          (event, order) -> order,
          Joined.with(Serdes.Long(), eventSerde, orderSerde));

      // Total income
      paidOrders
          .groupBy((key, order) -> TOTAL_INCOME_STORE_KEY, Grouped.with(Serdes.String(), orderSerde))
          .aggregate(
              () -> 0D,
              (orderId, order, sum) -> sum + order.getTotalPrice(),
              Materialized.<String, Double, KeyValueStore<Bytes, byte[]>>as(TOTAL_INCOME_STORE)
                  .withKeySerde(Serdes.String())
                  .withValueSerde(Serdes.Double()));

      paidOrders
          .map((key, order) -> KeyValue.pair(key, order.getId()))
          .toTable(Materialized.<Long, Long, KeyValueStore<Bytes, byte[]>>as(PAID_ORDER_IDS_STORE)
              .withKeySerde(Serdes.Long())
              .withValueSerde(Serdes.Long()));

      return paidOrders;
    };
  }

}
