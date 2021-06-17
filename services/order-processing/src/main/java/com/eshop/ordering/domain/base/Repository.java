package com.eshop.ordering.domain.base;

import org.springframework.lang.NonNull;

public interface Repository<T extends AggregateRoot> {
  T save(@NonNull T aggregateRoot);
}
