package com.eshop.gateway.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CreateOrderDraftRequest(
    @JsonProperty("buyerId") String buyerId,
    @JsonProperty("items") List<BasketItem> items
) {
}
