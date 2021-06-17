package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.base.Repository;
import org.springframework.lang.NonNull;

import java.util.Optional;

/**
 * This is just the RepositoryContracts or Interface defined at the Domain Layer
 * as requisite for the Order Aggregate.
 * <p>
 * Dispatch Domain Events collection.
 * Choices:
 * A) Right BEFORE committing data into the DB will make a single transaction including
 * side effects from the domain event handlers which are using the same DbContext with "InstancePerLifetimeScope" or "scoped" lifetime
 * <p>
 * B) Right AFTER committing data into the DB will make multiple transactions.
 * You will need to handle eventual consistency and compensatory actions in case of failures in any of the Handlers.
 */
public interface OrderRepository extends Repository<Order> {

  Order save(@NonNull Order order);

  Optional<Order> findById(@NonNull OrderId id);
}
