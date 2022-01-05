package com.eshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.UUID;

public record BasketData(
  @JsonProperty("id") UUID id,
  @JsonProperty("buyerId") String buyerId,
  @JsonProperty("items") List<BasketDataItem> items
) {
}
