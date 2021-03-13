package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import com.eshop.ordering.domain.seedwork.Entity;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

import static java.util.Objects.isNull;

@javax.persistence.Entity
public class PaymentMethod extends Entity {
  @Column(nullable = false, length = 200)
  private String alias;
  @Column(nullable = false, length = 25)
  private String cardNumber;
  @Column(nullable = false, length = 200)
  private String securityNumber;
  @Column(nullable = false, length = 200)
  private String cardHolderName;
  private LocalDate expiration;

  private int cardTypeId;

  @Transient
  @Getter
  @Setter(AccessLevel.PRIVATE)
  private CardType cardType;

  @ManyToOne(targetEntity = Buyer.class)
  @JoinColumn(name = "buyer_id", nullable = false)
  private Buyer buyer;

  protected PaymentMethod() {
  }

  public PaymentMethod(int cardTypeId, String alias, String cardNumber, String securityNumber, String cardHolderName, LocalDate expiration, Buyer buyer) {
    if (isNull(cardNumber)) {
      throw new OrderingDomainException("Card number");
    }

    if (isNull(securityNumber)) {
      throw new OrderingDomainException("Security number");
    }

    if (isNull(cardHolderName)) {
      throw new OrderingDomainException("Card holder name");
    }

    if (expiration.isBefore(LocalDate.now())) {
      throw new OrderingDomainException("Expiration");
    }

    this.cardNumber = cardNumber;
    this.securityNumber = securityNumber;
    this.cardHolderName = cardHolderName;
    this.alias = alias;
    this.expiration = expiration;
    this.cardTypeId = cardTypeId;
    this.buyer = buyer;
    this.populateCardType();
  }

  public boolean isEqualTo(int cardTypeId, String cardNumber, LocalDate expiration) {
    return this.cardTypeId == cardTypeId
        && this.cardNumber.equals(cardNumber)
        && this.expiration.equals(expiration);
  }

  @PostLoad
  void populateCardType() {
    if (cardTypeId > 0) {
      this.cardType = CardType.from(cardTypeId);
    }
  }

  @PrePersist
  void populateCardTypeId() {
    if (cardType != null) {
      this.cardTypeId = cardType.getId();
    }
  }

}
