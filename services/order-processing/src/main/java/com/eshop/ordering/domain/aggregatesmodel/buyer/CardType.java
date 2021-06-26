package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CardType {
  Amex("Amex"),
  Visa("Visa"),
  MasterCard("MasterCard");

  @Getter
  private final String cardName;

  public static CardType of(String cardName) {
    return Stream.of(values()).filter(s -> s.getCardName().equals(cardName))
        .findFirst()
        .orElseThrow(() -> new OrderingDomainException("Invalid card name: %s".formatted(cardName)));
  }
}
