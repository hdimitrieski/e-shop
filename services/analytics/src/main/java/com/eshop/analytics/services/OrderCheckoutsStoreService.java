package com.eshop.analytics.services;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import static com.eshop.analytics.common.Constants.CHECKOUTS_BY_USER_STORE;

@RequiredArgsConstructor
@Service
class OrderCheckoutsStoreService implements OrderCheckoutsService {
  private final InteractiveQueryService interactiveQueryService;

  @Override
  public Long checkoutsForUser(String userId) {
    final ReadOnlyKeyValueStore<String, Long> checkoutsByUserStore = interactiveQueryService.getQueryableStore(
        CHECKOUTS_BY_USER_STORE,
        QueryableStoreTypes.keyValueStore()
    );

    return checkoutsByUserStore.get(userId);
  }

  @Override
  public Long totalCheckoutsNumber() {
    final ReadOnlyKeyValueStore<String, Long> checkoutsByUserStore = interactiveQueryService.getQueryableStore(
        CHECKOUTS_BY_USER_STORE,
        QueryableStoreTypes.keyValueStore()
    );

    return calculateTotal(checkoutsByUserStore);
  }

  private Long calculateTotal(ReadOnlyKeyValueStore<String, Long> checkoutsByUserStore) {
    final var userCheckoutsIterator = checkoutsByUserStore.all();
    var total = 0L;

    while (userCheckoutsIterator.hasNext()) {
      final var userCheckouts = userCheckoutsIterator.next();
      total += userCheckouts.value;
    }

    return total;
  }
}
