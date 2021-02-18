package com.eshop.ordering.domain.seedwork;

import org.springframework.data.jpa.repository.JpaRepository;

public interface Repository<T extends AggregateRoot> extends JpaRepository<T, Integer> {
}
