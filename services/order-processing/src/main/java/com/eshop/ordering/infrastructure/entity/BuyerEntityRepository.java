package com.eshop.ordering.infrastructure.entity;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
interface BuyerEntityRepository extends CrudRepository<BuyerEntity, UUID> {
  Optional<BuyerEntity> findByUserId(String userId);
}
