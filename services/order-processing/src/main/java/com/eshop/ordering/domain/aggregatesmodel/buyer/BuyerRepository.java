package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.seedwork.Repository;

import java.util.Optional;

/**
 * This is just the RepositoryContracts or Interface defined at the Domain Layer
 * as requisite for the Buyer Aggregate.
 */
public interface BuyerRepository extends Repository<Buyer> {
  Buyer findByIdentityGuid(String buyerIdentityGuid);

  Optional<Buyer> findById(Long id);
}
