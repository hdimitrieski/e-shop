package com.eshop.gateway.models;

public record OrderItemData(
    Long productId,
    String productName,
    Double unitPrice,
    Double discount,
    Integer units,
    String pictureUrl
) {
}
