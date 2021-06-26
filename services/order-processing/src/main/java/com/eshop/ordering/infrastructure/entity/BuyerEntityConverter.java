package com.eshop.ordering.infrastructure.entity;

import com.eshop.ordering.domain.aggregatesmodel.buyer.*;
import com.eshop.ordering.domain.aggregatesmodel.buyer.snapshot.BuyerSnapshot;
import com.eshop.ordering.domain.aggregatesmodel.buyer.snapshot.PaymentMethodSnapshot;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
class BuyerEntityConverter implements EntityConverter<BuyerEntity, Buyer> {
  @Override
  public BuyerEntity toEntity(Buyer buyer) {
    var snapshot = buyer.snapshot();
    var buyerEntity = BuyerEntity.builder()
        .id(UUID.fromString(snapshot.getId()))
        .userId(snapshot.getUserId())
        .name(snapshot.getBuyerName())
        .build();

    buyerEntity.setPaymentMethods(toPaymentMethodEntities(snapshot.getPaymentMethods(), buyerEntity));

    return buyerEntity;
  }

  private Set<PaymentMethodEntity> toPaymentMethodEntities(List<PaymentMethodSnapshot> snapshots, BuyerEntity buyerEntity) {
    return snapshots.stream()
        .map(paymentMethod -> toPaymentMethodEntity(paymentMethod, buyerEntity))
        .collect(Collectors.toSet());
  }

  private PaymentMethodEntity toPaymentMethodEntity(PaymentMethodSnapshot snapshot, BuyerEntity buyerEntity) {
    return PaymentMethodEntity
        .builder()
        .id(UUID.fromString(snapshot.getId()))
        .alias(snapshot.getAlias())
        .cardNumber(snapshot.getCardNumber())
        .cardHolderName(snapshot.getCardHolderName())
        .expiration(snapshot.getExpiration())
        .securityNumber(snapshot.getSecurityNumber())
        .cardType(snapshot.getCardType())
        .buyer(buyerEntity)
        .build();
  }

  @Override
  public Buyer fromEntity(BuyerEntity entity) {
    return Buyer.rehydrate(BuyerSnapshot.builder()
        .id(entity.getId().toString())
        .userId(entity.getUserId())
        .buyerName(entity.getName())
        .paymentMethods(toPaymentMethodSnapshots(entity.getPaymentMethods()))
        .build());
  }

  private List<PaymentMethodSnapshot> toPaymentMethodSnapshots(Set<PaymentMethodEntity> entities) {
    return entities.stream()
        .map(this::toPaymentMethodSnapshot)
        .collect(Collectors.toList());
  }

  private PaymentMethodSnapshot toPaymentMethodSnapshot(PaymentMethodEntity entity) {
    return PaymentMethodSnapshot.builder()
        .id(entity.getId().toString())
        .alias(entity.getAlias())
        .cardHolderName(entity.getCardHolderName())
        .cardNumber(entity.getCardNumber())
        .cardType(entity.getCardType())
        .expiration(entity.getExpiration())
        .securityNumber(entity.getSecurityNumber())
        .build();
  }

}
