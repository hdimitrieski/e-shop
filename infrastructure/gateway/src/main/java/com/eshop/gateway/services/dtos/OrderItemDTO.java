package com.eshop.gateway.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record OrderItemDTO(
    @JsonProperty("productId") UUID productId,
    @JsonProperty("productName") String productName,
    @JsonProperty("unitPrice") Double unitPrice,
    @JsonProperty("discount") Double discount,
    @JsonProperty("units") Integer units,
    @JsonProperty("pictureUrl") String pictureUrl
) {
}
