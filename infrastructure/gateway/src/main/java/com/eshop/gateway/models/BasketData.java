package com.eshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record BasketData(
    @JsonProperty("buyerId") String buyerId,
    @JsonProperty("items") List<BasketDataItem> items
) {
}
