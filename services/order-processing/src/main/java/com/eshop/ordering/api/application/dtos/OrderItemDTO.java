package com.eshop.ordering.api.application.dtos;

import java.util.UUID;

public record OrderItemDTO(
    UUID productId,
    String productName,
    Double unitPrice,
    Double discount,
    Integer units,
    String pictureUrl
) {
}
