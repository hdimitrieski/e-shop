package com.eshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

public record OrderData(
    @JsonProperty("orderNumber") String orderNumber,
    @JsonProperty("date") LocalDateTime date,
    @JsonProperty("status") String status,
    @JsonProperty("total") Double total,
    @JsonProperty("description") String description,
    @JsonProperty("city") String city,
    @JsonProperty("street") String street,
    @JsonProperty("state") String state,
    @JsonProperty("country") String country,
    @JsonProperty("zipCode") String zipCode,
    @JsonProperty("cardNumber") String cardNumber,
    @JsonProperty("cardHolderName") String cardHolderName,
    boolean isDraft,
    @JsonProperty("cardExpiration") LocalDate cardExpiration,
    @JsonProperty("cardSecurityNumber") String cardSecurityNumber,
    @JsonProperty("cardType") String cardType,
    @JsonProperty("buyer") String buyer,
    @JsonProperty("orderItems") List<OrderItemData> orderItems
) {
  public OrderData(String buyer, Double total, List<OrderItemData> orderItems) {
    this(null, null, null, total, null, null, null, null,
        null, null, null, null, true, null,
        null, null, buyer, orderItems);
  }
}
