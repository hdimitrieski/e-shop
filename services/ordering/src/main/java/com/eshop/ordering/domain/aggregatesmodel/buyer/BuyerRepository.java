package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.seedwork.Repository;

/**
 * This is just the RepositoryContracts or Interface defined at the Domain Layer
 * as requisite for the Buyer Aggregate.
 */
public interface BuyerRepository extends Repository<Buyer> {
//    Buyer add(Buyer buyer);
//    Buyer update(Buyer buyer);
    Buyer findByIdentityGuid(String buyerIdentityGuid);
}
