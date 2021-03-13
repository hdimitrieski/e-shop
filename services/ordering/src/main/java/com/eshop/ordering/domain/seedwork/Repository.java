package com.eshop.ordering.domain.seedwork;

public interface Repository<T extends AggregateRoot> {
  T save(T aggregateRoot);
}
