package com.eshop.gateway.models;

public record UpdateBasketRequestItemData(
    String id,
    Long productId,
    Integer quantity
) {
}
