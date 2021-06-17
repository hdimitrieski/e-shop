package com.eshop.ordering.infrastructure.entity;

import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderId;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
class OrderRepositoryImpl implements OrderRepository {
  private final OrderEntityConverter orderEntityConverter;
  private final ApplicationEventPublisher applicationEventPublisher;

  @PersistenceContext
  private final EntityManager entityManager;

  @Override
  public Order save(@NonNull Order aggregateRoot) {
    var orderEntity = orderEntityConverter.toEntity(aggregateRoot);
    entityManager.merge(orderEntity);

    aggregateRoot.domainEvents().forEach(applicationEventPublisher::publishEvent);
    aggregateRoot.clearDomainEvents();

    return orderEntityConverter.fromEntity(orderEntity);
  }

  @Override
  public Optional<Order> findById(@NonNull OrderId id) {
    return Optional.ofNullable(entityManager.find(OrderEntity.class, id.getValue()))
        .map(orderEntityConverter::fromEntity);
  }
}
