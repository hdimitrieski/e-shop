package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.events.BuyerAndPaymentMethodVerifiedDomainEvent;
import com.eshop.ordering.domain.seedwork.AggregateRoot;
import lombok.Getter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.isNull;

@Entity
@Table(name = "buyer", uniqueConstraints = @UniqueConstraint(columnNames = {"user_id"}))
public class Buyer extends AggregateRoot {

  @Getter
  @Column(name = "user_id", nullable = false, length = 200, unique = true)
  private String userId;

  @Getter
  @Column(name = "name")
  private String name;

  @OneToMany(cascade = CascadeType.ALL, mappedBy = "buyer")
  @OrderBy("id")
  @Getter
  private List<PaymentMethod> paymentMethods;

  protected Buyer() {
    paymentMethods = new ArrayList<>();
  }

  public Buyer(String userId, String name) {
    this();

    if (isNull(userId)) {
      throw new IllegalArgumentException("userId");
    }

    if (isNull(name)) {
      throw new IllegalArgumentException("Name");
    }
    this.userId = userId;
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

    var payment = new PaymentMethod(cardTypeId, alias, cardNumber, securityNumber, cardHolderName, expiration, this);
    paymentMethods.add(payment);
    addDomainEvent(new BuyerAndPaymentMethodVerifiedDomainEvent(this, payment, orderId));
    return payment;
  }
}
