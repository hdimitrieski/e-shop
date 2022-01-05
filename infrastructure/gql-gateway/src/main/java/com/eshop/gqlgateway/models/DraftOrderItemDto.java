package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record DraftOrderItemDto(
  @JsonProperty("productId") UUID productId,
  @JsonProperty("productName") String productName,
  @JsonProperty("unitPrice") Double unitPrice,
  @JsonProperty("units") Integer units,
  @JsonProperty("pictureUrl") String pictureUrl
) {
}
