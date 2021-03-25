package com.eshop.gateway.services.dtos;

public record BasketItem(
    String id,
    Long productId,
    String productName,
    Double unitPrice,
    Double oldUnitPrice,
    Integer quantity,
    String pictureUrl
) {
}
