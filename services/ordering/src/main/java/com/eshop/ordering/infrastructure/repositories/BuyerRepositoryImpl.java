package com.eshop.ordering.infrastructure.repositories;

import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class BuyerRepositoryImpl implements BuyerRepository {
  private final BuyerJpaRepository buyerJpaRepository;
  private final ApplicationEventPublisher applicationEventPublisher;
  private final EntityManager entityManager;

  @Override
  public Buyer save(Buyer aggregateRoot) {
    entityManager.persist(aggregateRoot);
    aggregateRoot.domainEvents().forEach(applicationEventPublisher::publishEvent);
    aggregateRoot.clearDomainEvents();

    return aggregateRoot;
  }

  @Override
  public Buyer findByIdentityGuid(String buyerIdentityGuid) {
    return buyerJpaRepository.findByIdentityGuid(buyerIdentityGuid);
  }

  @Override
  public Optional<Buyer> findById(Long id) {
    return buyerJpaRepository.findById(id);
  }
}
