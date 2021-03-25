package com.eshop.gateway.models;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderData(
    String orderNumber,
    LocalDateTime date,
    String status,
    Double total,
    String description,
    String city,
    String street,
    String state,
    String country,
    String zipCode,
    String cardNumber,
    String cardHolderName,
    boolean isDraft,
    LocalDate cardExpiration,
    String cardSecurityNumber,
    Integer cardTypeId,
    String buyer,
    List<OrderItemData> orderItems
) {
  public OrderData(String buyer, Double total, List<OrderItemData> orderItems) {
    this(null, null, null, total, null, null, null, null,
        null, null, null, null, true, null,
        null, null, buyer, orderItems);
  }
}
