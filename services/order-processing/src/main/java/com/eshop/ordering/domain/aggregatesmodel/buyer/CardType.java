package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum CardType {
  Amex(1, "Amex"),
  Visa(2, "Visa"),
  MasterCard(3, "MasterCard");

  @Getter
  private final Integer id;

  @Getter
  private final String name;

  public static CardType from(Integer id) {
    return Stream.of(values()).filter(s -> s.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new OrderingDomainException("Invalid id for CardType: %d".formatted(id)));
  }
}
