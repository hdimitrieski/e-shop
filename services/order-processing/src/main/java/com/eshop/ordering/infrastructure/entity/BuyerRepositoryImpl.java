package com.eshop.ordering.infrastructure.entity;

import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerId;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.buyer.UserId;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@AllArgsConstructor
class BuyerRepositoryImpl implements BuyerRepository {
  private final BuyerEntityRepository buyerEntityRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final BuyerEntityConverter buyerEntityConverter;
  private final EntityManager entityManager;

  @Override
  public Buyer save(@NonNull Buyer aggregateRoot) {
    var buyerEntity = buyerEntityConverter.toEntity(aggregateRoot);
    entityManager.merge(buyerEntity);
    aggregateRoot.domainEvents().forEach(applicationEventPublisher::publishEvent);
    aggregateRoot.clearDomainEvents();

    return buyerEntityConverter.fromEntity(buyerEntity);
  }

  @Override
  public Optional<Buyer> findByUserId(@NonNull UserId userId) {
    return buyerEntityRepository.findByUserId(userId.getId())
        .map(buyerEntityConverter::fromEntity);
  }

  @Override
  public Optional<Buyer> findById(@NonNull BuyerId id) {
    return buyerEntityRepository.findById(id.getValue())
        .map(buyerEntityConverter::fromEntity);
  }
}
