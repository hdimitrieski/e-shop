package com.eshop.gateway.models;

public record BasketDataItem(
    String id,
    Long productId,
    String productName,
    Double unitPrice,
    Double oldUnitPrice,
    Integer quantity,
    String pictureUrl
) {
}
