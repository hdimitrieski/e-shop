package com.eshop.ordering.infrastructure.repositories;

import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class OrderRepositoryImpl implements OrderRepository {
  private final OrderJpaRepository orderJpaRepository;
  private final ApplicationEventPublisher applicationEventPublisher;

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public Order save(Order aggregateRoot) {
    entityManager.persist(aggregateRoot);
    aggregateRoot.domainEvents().forEach(applicationEventPublisher::publishEvent);
    aggregateRoot.clearDomainEvents();

    return aggregateRoot;
  }

  @Override
  public Optional<Order> findById(Long id) {
    return Optional.ofNullable(entityManager.find(Order.class, id));
  }
}
