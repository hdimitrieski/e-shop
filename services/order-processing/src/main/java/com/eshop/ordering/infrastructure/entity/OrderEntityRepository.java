package com.eshop.ordering.infrastructure.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
interface OrderEntityRepository extends CrudRepository<OrderEntity, UUID> {
}
