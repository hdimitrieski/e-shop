package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record OrderDto(
  @JsonProperty("id") UUID id,
  @JsonProperty("orderNumber") String orderNumber,
  @JsonProperty("date") LocalDateTime date,
  @JsonProperty("status") String status,
  @JsonProperty("description") String description,
  @JsonProperty("street") String street,
  @JsonProperty("city") String city,
  @JsonProperty("state") String state,
  @JsonProperty("zipCode") String zipCode,
  @JsonProperty("country") String country,
  @JsonProperty("total") Double total,
  @JsonProperty("orderItems") List<OrderItemDto> orderItems
) {
}
