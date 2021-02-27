package com.eshop.ordering.domain.seedwork;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;

public interface Repository<T extends AggregateRoot> extends CrudRepository<T, Long> {
}
