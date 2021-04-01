package com.eshop.gateway.services.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record OrderDraftDTO(
    @JsonProperty("orderItems") List<OrderItemDTO> orderItems,
    @JsonProperty("total") Double total
) {
}
