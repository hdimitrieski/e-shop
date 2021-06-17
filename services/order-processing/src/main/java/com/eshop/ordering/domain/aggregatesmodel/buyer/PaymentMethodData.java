package com.eshop.ordering.domain.aggregatesmodel.buyer;

import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.Objects;

@Getter
public class PaymentMethodData {
  private final CardType cardType;
  private final String alias;
  private final CardNumber cardNumber;
  private final SecurityNumber securityNumber;
  private final CardHolder cardHolderName;
  private final CardExpiration expiration;

  @Builder
  public PaymentMethodData(
      @NonNull CardType cardType,
      @NonNull String alias,
      @NonNull CardNumber cardNumber,
      @NonNull SecurityNumber securityNumber,
      @NonNull CardHolder cardHolderName,
      @NonNull CardExpiration expiration
  ) {
    this.cardType = Objects.requireNonNull(cardType, "Card type cannot be null");
    this.alias = Objects.requireNonNull(alias, "Alias cannot be null");
    this.cardNumber = Objects.requireNonNull(cardNumber, "Card number cannot be null");
    this.securityNumber = Objects.requireNonNull(securityNumber, "Security number cannot be null");
    this.cardHolderName = Objects.requireNonNull(cardHolderName, "Card holder name cannot be null");
    this.expiration = Objects.requireNonNull(expiration, "Expiration cannot be null");
  }
}
