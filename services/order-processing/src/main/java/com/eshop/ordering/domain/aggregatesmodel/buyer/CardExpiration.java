package com.eshop.ordering.domain.aggregatesmodel.buyer;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import com.eshop.ordering.domain.base.ValueObject;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Getter
@ToString
public class CardExpiration extends ValueObject {
  private final LocalDate date;

  private CardExpiration(LocalDate date) {
    if (date.isBefore(LocalDate.now())) {
      throw new OrderingDomainException("The card is expired");
    }

    this.date = date;
  }

  public static CardExpiration of(LocalDate date) {
    return new CardExpiration(date);
  }

  @Override
  protected List<Object> getEqualityComponents() {
    return List.of(date);
  }
}
