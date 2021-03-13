package com.eshop.ordering.api.application.dtos;

public record OrderItemDTO(
    Integer productId,
    String productName,
    Double unitPrice,
    Double discount,
    Integer units,
    String pictureUrl
) {
}
