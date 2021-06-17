package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.base.Repository;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * This is just the RepositoryContracts or Interface defined at the Domain Layer
 * as requisite for the Buyer Aggregate.
 */
public interface BuyerRepository extends Repository<Buyer> {
  Optional<Buyer> findByUserId(@NonNull UserId userId);

  Optional<Buyer> findById(@NonNull BuyerId id);
}
