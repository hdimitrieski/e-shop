package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.aggregatesmodel.buyer.*;
import com.eshop.ordering.domain.base.ValueObject;
import lombok.Builder;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.List;
import java.util.Objects;

@Getter
public class NewOrderData extends ValueObject {
  private final UserId userId;
  private final BuyerName buyerName;
  private final Address address;
  private final CardType cardType;
  private final CardNumber cardNumber;
  private final SecurityNumber cardSecurityNumber;
  private final CardHolder cardHolderName;
  private final CardExpiration cardExpiration;

  @Builder
  private NewOrderData(
      @NonNull UserId userId,
      @NonNull BuyerName buyerName,
      @NonNull Address address,
      @NonNull CardType cardType,
      @NonNull CardNumber cardNumber,
      @NonNull SecurityNumber cardSecurityNumber,
      @NonNull CardHolder cardHolderName,
      @NonNull CardExpiration cardExpiration
  ) {
    this.userId = Objects.requireNonNull(userId, "User id cannot be null");
    this.buyerName = Objects.requireNonNull(buyerName, "Buyer name cannot be null");
    this.address = Objects.requireNonNull(address, "Address cannot be null");
    this.cardType = Objects.requireNonNull(cardType, "Card type cannot be null");
    this.cardNumber = Objects.requireNonNull(cardNumber, "Card number cannot be null");
    this.cardSecurityNumber = Objects.requireNonNull(cardSecurityNumber, "Security number cannot be null");
    this.cardHolderName = Objects.requireNonNull(cardHolderName, "Card holder cannot be null");
    this.cardExpiration = Objects.requireNonNull(cardExpiration, "Card expiration cannot be null");
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(userId, buyerName, address, cardType, cardNumber, cardSecurityNumber, cardHolderName, cardExpiration);
  }
}
