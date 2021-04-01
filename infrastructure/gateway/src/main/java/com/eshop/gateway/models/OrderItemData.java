package com.eshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderItemData(
    @JsonProperty("productId") Long productId,
    @JsonProperty("productName") String productName,
    @JsonProperty("unitPrice") Double unitPrice,
    @JsonProperty("discount") Double discount,
    @JsonProperty("units") Integer units,
    @JsonProperty("pictureUrl") String pictureUrl
) {
}
