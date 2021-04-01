package com.eshop.gateway.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

public record BasketItem(
    @JsonProperty("id") String id,
    @JsonProperty("productId") Long productId,
    @JsonProperty("productName") String productName,
    @JsonProperty("unitPrice") Double unitPrice,
    @JsonProperty("oldUnitPrice") Double oldUnitPrice,
    @JsonProperty("quantity") Integer quantity,
    @JsonProperty("pictureUrl") String pictureUrl
) {
}
