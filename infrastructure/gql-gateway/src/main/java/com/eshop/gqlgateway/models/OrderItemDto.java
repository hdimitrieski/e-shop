package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record OrderItemDto(
  @JsonProperty("id") UUID id,
  @JsonProperty("productId") UUID productId,
  @JsonProperty("productName") String productName,
  @JsonProperty("units") Integer units,
  @JsonProperty("unitPrice") Double unitPrice,
  @JsonProperty("pictureUrl") String pictureUrl
) {
}
