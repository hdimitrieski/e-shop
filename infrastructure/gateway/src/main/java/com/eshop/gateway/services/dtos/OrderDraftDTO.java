package com.eshop.gateway.services.dtos;

import java.util.List;

public record OrderDraftDTO(
    List<OrderItemDTO> orderItems,
    Double total
) {
}
