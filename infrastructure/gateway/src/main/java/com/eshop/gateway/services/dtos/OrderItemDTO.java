package com.eshop.gateway.services.dtos;

public record OrderItemDTO(
    Long productId,
    String productName,
    Double unitPrice,
    Double discount,
    Integer units,
    String pictureUrl
) {
}
