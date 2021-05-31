package com.eshop.analytics.services;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import static com.eshop.analytics.common.Constants.PAYMENTS_COUNT_STORE;
import static com.eshop.analytics.common.Constants.PAYMENTS_COUNT_STORE_KEY;

@RequiredArgsConstructor
@Service
public class PaymentsStoreService implements PaymentsService {
  private final InteractiveQueryService interactiveQueryService;

  @Override
  public Long count() {
    final ReadOnlyKeyValueStore<String, Long> paymentsCountStore = interactiveQueryService.getQueryableStore(
        PAYMENTS_COUNT_STORE,
        QueryableStoreTypes.keyValueStore()
    );

    return paymentsCountStore.get(PAYMENTS_COUNT_STORE_KEY);
  }
}
