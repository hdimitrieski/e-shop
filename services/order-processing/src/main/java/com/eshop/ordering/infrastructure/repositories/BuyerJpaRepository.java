package com.eshop.ordering.infrastructure.repositories;

import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import org.springframework.data.repository.CrudRepository;

public interface BuyerJpaRepository extends CrudRepository<Buyer, Long> {
  Buyer findByUserId(String buyerIdentityGuid);
}
