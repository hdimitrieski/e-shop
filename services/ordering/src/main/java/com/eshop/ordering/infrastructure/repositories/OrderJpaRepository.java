package com.eshop.ordering.infrastructure.repositories;

import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import org.springframework.data.repository.CrudRepository;

public interface OrderJpaRepository extends CrudRepository<Order, Long> {
}
