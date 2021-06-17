package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.aggregatesmodel.buyer.snapshot.BuyerSnapshot;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderId;
import com.eshop.ordering.domain.base.AggregateRoot;
import com.eshop.ordering.domain.events.BuyerAndPaymentMethodVerifiedDomainEvent;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.*;
import java.util.stream.Collectors;

public class Buyer extends AggregateRoot<BuyerId> {

  @Getter
  private final UserId userId;

  @Getter
  private final BuyerName buyerName;

  @Getter
  private final List<PaymentMethod> paymentMethods;

  private Buyer(
      @NonNull BuyerId id,
      @NonNull UserId userId,
      @NonNull BuyerName buyerName,
      @NonNull List<PaymentMethod> paymentMethods
  ) {
    this.id = Objects.requireNonNull(id, "Buyer id cannot be null");
    this.userId = Objects.requireNonNull(userId, "User id cannot be null");
    this.buyerName = Objects.requireNonNull(buyerName, "Buyer name cannot be null");
    this.paymentMethods = Objects.requireNonNull(paymentMethods, "Payment methods cannot be null");
  }

  public Buyer(@NonNull UserId userId, @NonNull BuyerName buyerName) {
    this(BuyerId.of(UUID.randomUUID()), userId, buyerName, new ArrayList<>());
  }

  @NonNull
  public static Buyer rehydrate(@NonNull BuyerSnapshot snapshot) {
    Objects.requireNonNull(snapshot, "Snapshot cannot be null");
    return new Buyer(
        BuyerId.of(snapshot.getId()),
        UserId.of(snapshot.getUserId()),
        BuyerName.of(snapshot.getBuyerName()),
        snapshot.getPaymentMethods().stream()
            .map(PaymentMethod::rehydrate)
            .collect(Collectors.toList())
    );
  }

  @NonNull
  public PaymentMethod verifyOrAddPaymentMethod(@NonNull PaymentMethodData paymentMethodData, @NonNull OrderId orderId) {
    Objects.requireNonNull(paymentMethodData, "Payment method data cannot be null");
    Objects.requireNonNull(orderId, "Order id cannot be null");

    final var paymentMethod = findExistingPayment(
        paymentMethodData.getCardType(),
        paymentMethodData.getCardNumber(),
        paymentMethodData.getExpiration()
    ).orElseGet(() -> addPaymentMethod(paymentMethodData));

    addDomainEvent(new BuyerAndPaymentMethodVerifiedDomainEvent(this, paymentMethod, orderId));
    return paymentMethod;
  }

  @NonNull
  @Override
  public BuyerSnapshot snapshot() {
    return BuyerSnapshot.builder()
        .id(id.getUuid())
        .userId(userId.getId())
        .buyerName(buyerName.getName())
        .paymentMethods(paymentMethods.stream().map(PaymentMethod::snapshot).collect(Collectors.toList()))
        .build();
  }

  private Optional<PaymentMethod> findExistingPayment(CardType cardType, CardNumber cardNumber, CardExpiration expiration) {
    return paymentMethods.stream()
        .filter(p -> p.isEqualTo(cardType, cardNumber, expiration))
        .findFirst();
  }

  @NonNull
  private PaymentMethod addPaymentMethod(@NonNull PaymentMethodData paymentMethodData) {
    final var paymentMethod = PaymentMethod.builder()
        .cardType(paymentMethodData.getCardType())
        .alias(paymentMethodData.getAlias())
        .cardNumber(paymentMethodData.getCardNumber())
        .securityNumber(paymentMethodData.getSecurityNumber())
        .cardHolderName(paymentMethodData.getCardHolderName())
        .expiration(paymentMethodData.getExpiration())
        .build();
    paymentMethods.add(paymentMethod);
    return paymentMethod;
  }

}
