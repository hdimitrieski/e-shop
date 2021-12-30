package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record BasketItemDto(
  @JsonProperty("id") UUID id,
  @JsonProperty("productId") UUID productId,
  @JsonProperty("productName") String productName,
  @JsonProperty("unitPrice") Double unitPrice,
  @JsonProperty("oldUnitPrice") Double oldUnitPrice,
  @JsonProperty("quantity") Integer quantity,
  @JsonProperty("pictureUrl") String pictureUrl
) {
}
