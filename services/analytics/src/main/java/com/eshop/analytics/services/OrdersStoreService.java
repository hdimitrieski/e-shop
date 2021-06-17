package com.eshop.analytics.services;

import com.eshop.analytics.model.Order;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import static com.eshop.analytics.common.Constants.PAID_ORDER_IDS_STORE;
import static com.eshop.analytics.common.Constants.SUBMITTED_ORDERS_STORE;
import static java.util.Optional.ofNullable;

@RequiredArgsConstructor
@Service
class OrdersStoreService implements OrdersService {
  private final InteractiveQueryService interactiveQueryService;

  @Override
  public Order getSubmittedOrder(String orderId) {
    final ReadOnlyKeyValueStore<String, Order> submittedOrdersStore = interactiveQueryService.getQueryableStore(
        SUBMITTED_ORDERS_STORE,
        QueryableStoreTypes.keyValueStore()
    );

    return submittedOrdersStore.get(orderId);
  }

  @Override
  public Order getPaidOrder(String orderId) {
    final ReadOnlyKeyValueStore<String, String> paidOrderIdsStore = interactiveQueryService.getQueryableStore(
        PAID_ORDER_IDS_STORE,
        QueryableStoreTypes.keyValueStore()
    );

    return ofNullable(paidOrderIdsStore.get(orderId))
        .map(this::getSubmittedOrder)
        .orElse(null);
  }
}
