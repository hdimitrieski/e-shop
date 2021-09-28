package com.eshop.gateway.models;

import java.util.UUID;

public record UpdateBasketRequestItemData(
    String id,
    UUID productId,
    Integer quantity
) {
}
