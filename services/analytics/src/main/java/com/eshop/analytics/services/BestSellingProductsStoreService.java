package com.eshop.analytics.services;

import com.eshop.analytics.model.Product;
import com.eshop.analytics.model.TopFiveProducts;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.common.serialization.StringSerializer;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

import static com.eshop.analytics.common.Constants.TOP_FIVE_PRODUCTS_STORE;
import static com.eshop.analytics.common.Constants.TOP_FIVE_PRODUCTS_STORE_KEY;

@RequiredArgsConstructor
@Service
class BestSellingProductsStoreService implements BestSellingProductsService {
  private static final Logger logger = LoggerFactory.getLogger(BestSellingProductsStoreService.class);

  private final InteractiveQueryService interactiveQueryService;

  @Override
  public List<Product> topFiveProducts() {
    final var hostInfo = interactiveQueryService.getHostInfo(
        TOP_FIVE_PRODUCTS_STORE,
        TOP_FIVE_PRODUCTS_STORE_KEY,
        new StringSerializer()
    );

    if (interactiveQueryService.getCurrentHostInfo().equals(hostInfo)) {
      logger.info("Top Five products request served from same host: {}", hostInfo);
      return topFiveProductsFromStore(TOP_FIVE_PRODUCTS_STORE_KEY, TOP_FIVE_PRODUCTS_STORE);
    }

    return topFiveProductsFromRemote(hostInfo);
  }

  private List<Product> topFiveProductsFromStore(final String key, final String storeName) {
      final ReadOnlyKeyValueStore<String, TopFiveProducts> topFiveStore =
          interactiveQueryService.getQueryableStore(storeName, QueryableStoreTypes.keyValueStore());

      final var topFiveProducts = topFiveStore.get(key);
      if (topFiveProducts == null) {
        throw new IllegalArgumentException(String.format("Unable to find value in %s for key %s", storeName, key));
      }

      final var results = new ArrayList<Product>();

      topFiveProducts.forEach(productSellCount -> {
        results.add(new Product(productSellCount.getProductId()));
      });
      return results;
  }

  @SuppressWarnings("unchecked")
  private List<Product> topFiveProductsFromRemote(HostInfo hostInfo) {
    // find the store from the proper instance.
    logger.info("Top Five products request served from different host: {}", hostInfo);
    final var restTemplate = new RestTemplate();
    final var url = String.format("http://%s:%d/%s", hostInfo.host(), hostInfo.port(), "products/top-five");
    return restTemplate.postForObject(url, "topfive", List.class);
  }

}
