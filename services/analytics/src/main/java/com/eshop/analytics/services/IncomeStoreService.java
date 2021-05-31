package com.eshop.analytics.services;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;
import org.springframework.cloud.stream.binder.kafka.streams.InteractiveQueryService;
import org.springframework.stereotype.Service;

import static com.eshop.analytics.common.Constants.TOTAL_INCOME_STORE;
import static com.eshop.analytics.common.Constants.TOTAL_INCOME_STORE_KEY;

@RequiredArgsConstructor
@Service
public class IncomeStoreService implements IncomeService {
  private final InteractiveQueryService interactiveQueryService;

  @Override
  public Double totalIncome() {
    final ReadOnlyKeyValueStore<String, Double> incomeStore = interactiveQueryService.getQueryableStore(
        TOTAL_INCOME_STORE,
        QueryableStoreTypes.keyValueStore()
    );

    return incomeStore.get(TOTAL_INCOME_STORE_KEY);
  }
}
