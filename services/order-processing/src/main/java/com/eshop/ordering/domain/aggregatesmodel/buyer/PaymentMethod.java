package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.aggregatesmodel.buyer.snapshot.PaymentMethodSnapshot;
import com.eshop.ordering.domain.base.Entity;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

public class PaymentMethod extends Entity<PaymentMethodId> {
  private final String alias;
  private final CardNumber cardNumber;
  private final SecurityNumber securityNumber;
  private final CardHolder cardHolderName;
  private final CardExpiration expiration;
  private final CardType cardType;

  private PaymentMethod(
      PaymentMethodId id,
      CardType cardType,
      String alias,
      CardNumber cardNumber,
      SecurityNumber securityNumber,
      CardHolder cardHolderName,
      CardExpiration expiration
  ) {
    this.id = Objects.requireNonNull(id, "Id cannot be null");
    this.cardType = Objects.requireNonNull(cardType, "Card type cannot be null");
    this.alias = Objects.requireNonNull(alias, "Alias cannot be null");
    this.cardNumber = Objects.requireNonNull(cardNumber, "Card number cannot be null");
    this.securityNumber = Objects.requireNonNull(securityNumber, "Security number cannot be null");
    this.cardHolderName = Objects.requireNonNull(cardHolderName, "Card holder name cannot be null");
    this.expiration = Objects.requireNonNull(expiration, "Expiration cannot be null");
  }

  @Builder(access = AccessLevel.PACKAGE)
  private PaymentMethod(
      @NonNull CardType cardType,
      @NonNull String alias,
      @NonNull CardNumber cardNumber,
      @NonNull SecurityNumber securityNumber,
      @NonNull CardHolder cardHolderName,
      @NonNull CardExpiration expiration
  ) {
    this(PaymentMethodId.of(UUID.randomUUID()), cardType, alias, cardNumber, securityNumber, cardHolderName, expiration);
  }

  @NonNull
  public static PaymentMethod rehydrate(@NonNull PaymentMethodSnapshot snapshot) {
    Objects.requireNonNull(snapshot, "Snapshot cannot be null");
    return new PaymentMethod(
        PaymentMethodId.of(snapshot.getId()),
        CardType.of(snapshot.getCardType()),
        snapshot.getAlias(),
        CardNumber.of(snapshot.getCardNumber()),
        SecurityNumber.of(snapshot.getSecurityNumber()),
        CardHolder.of(snapshot.getCardHolderName()),
        CardExpiration.of(snapshot.getExpiration())
    );
  }

  @NonNull
  public CardType cardType() {
    return cardType;
  }

  public boolean isEqualTo(CardType cardType, CardNumber cardNumber, CardExpiration expiration) {
    return this.cardType == cardType
        && this.cardNumber.equals(cardNumber)
        && this.expiration.equals(expiration);
  }

  @NonNull
  @Override
  public PaymentMethodSnapshot snapshot() {
    return PaymentMethodSnapshot.builder()
        .id(id.getUuid())
        .cardType(cardType.getCardName())
        .securityNumber(securityNumber.getValue())
        .expiration(expiration.getDate())
        .cardNumber(cardNumber.getValue())
        .cardHolderName(cardHolderName.getValue())
        .alias(alias)
        .build();
  }
}
