package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.seedwork.Repository;

import java.util.Optional;

/**
 * This is just the RepositoryContracts or Interface defined at the Domain Layer
 * as requisite for the Order Aggregate.
 */
public interface OrderRepository extends Repository<Order> {

  Order save(Order order);

//    void update(Order order);

  Optional<Order> findById(Long id);

  // TODO HD in the repo implementation. see OrderingContext
  // Also instead of @Transactional i can manually handle the transactions and implement UnitOfWork pattern...
  // Dispatch Domain Events collection.
  // Choices:
  // A) Right BEFORE committing data (EF SaveChanges) into the DB will make a single transaction including
  // side effects from the domain event handlers which are using the same DbContext with "InstancePerLifetimeScope" or "scoped" lifetime
  // B) Right AFTER committing data (EF SaveChanges) into the DB will make multiple transactions.
  // You will need to handle eventual consistency and compensatory actions in case of failures in any of the Handlers.
}
