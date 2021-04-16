package com.eshop.ordering.api.application.dtos;

public record OrderItemDTO(
    Long productId,
    String productName,
    Double unitPrice,
    Double discount,
    Integer units,
    String pictureUrl
) {
}
