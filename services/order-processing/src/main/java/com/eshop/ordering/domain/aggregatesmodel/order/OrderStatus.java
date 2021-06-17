package com.eshop.ordering.domain.aggregatesmodel.order;

import com.eshop.ordering.domain.exceptions.OrderingDomainException;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum OrderStatus {
  Submitted(1, "Submitted"),
  AwaitingValidation(2, "AwaitingValidation"),
  StockConfirmed(3, "StockConfirmed"),
  Paid(4, "Paid"),
  Shipped(5, "Shipped"),
  Cancelled(6, "Cancelled");

  @Getter
  private final Integer id;
  @Getter
  private final String status;

  public static OrderStatus fromStatus(@NonNull String status) {
    return Stream.of(values()).filter(s -> s.getStatus().equals(status))
        .findFirst()
        .orElseThrow(() -> new OrderingDomainException("Invalid name for OrderStatus: %s".formatted(status)));
  }

  public static OrderStatus of(@NonNull Integer id) {
    return Stream.of(values()).filter(s -> s.getId().equals(id))
        .findFirst()
        .orElseThrow(() -> new OrderingDomainException("Invalid value for OrderStatus: %d".formatted(id)));
  }
}
