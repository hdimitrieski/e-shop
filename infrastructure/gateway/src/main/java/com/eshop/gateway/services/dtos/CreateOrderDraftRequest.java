package com.eshop.gateway.services.dtos;

import java.util.List;

public record CreateOrderDraftRequest(
    String buyerId,
    List<BasketItem> items
) {
}
