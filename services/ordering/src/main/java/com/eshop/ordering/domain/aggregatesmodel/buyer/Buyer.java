package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.events.BuyerAndPaymentMethodVerifiedDomainEvent;
import com.eshop.ordering.domain.seedwork.AggregateRoot;
import lombok.Getter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
public class Buyer extends AggregateRoot {
  @Column(nullable = false, length = 200, unique = true)
  @Getter
  private String identityGuid;
  @Getter
  private String name;
  @OneToMany
  @JoinColumn(name = "buyer_id")
  @Getter
  private List<PaymentMethod> paymentMethods;

  protected Buyer() {
    paymentMethods = new ArrayList<>();
  }

  public Buyer(String identity, String name) {
    this();

    if (isNull(identity)) {
      throw new IllegalArgumentException("Identity");
    }

    if (isNull(name)) {
      throw new IllegalArgumentException("Name");
    }
    this.identityGuid = identity;
    this.name = name;
  }

  public PaymentMethod verifyOrAddPaymentMethod(
      int cardTypeId, String alias, String cardNumber,
      String securityNumber, String cardHolderName, LocalDate expiration, Long orderId) {
    var existingPayment = paymentMethods.stream()
        .filter(p -> p.isEqualTo(cardTypeId, cardNumber, expiration))
        .findFirst()
        .orElse(null);

    if (existingPayment != null) {
      addDomainEvent(new BuyerAndPaymentMethodVerifiedDomainEvent(this, existingPayment, orderId));
      return existingPayment;
    }

    var payment = new PaymentMethod(cardTypeId, alias, cardNumber, securityNumber, cardHolderName, expiration);
    paymentMethods.add(payment);
    addDomainEvent(new BuyerAndPaymentMethodVerifiedDomainEvent(this, payment, orderId));
    return payment;
  }
}
