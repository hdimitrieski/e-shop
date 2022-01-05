package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record DraftOrderDto(
  @JsonProperty("total") Double total,
  @JsonProperty("orderItems") List<DraftOrderItemDto> orderItems
) {
}
