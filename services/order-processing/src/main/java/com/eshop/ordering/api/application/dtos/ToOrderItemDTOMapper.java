package com.eshop.ordering.api.application.dtos;

import com.eshop.ordering.api.application.models.BasketItem;

public class ToOrderItemDTOMapper {
  public OrderItemDTO map(BasketItem basketItem) {
    return new OrderItemDTO(
        basketItem.getProductId(),
        basketItem.getProductName(),
        basketItem.getUnitPrice(),
        0D, // basketItem.getDiscount(),
        basketItem.getQuantity(),
        basketItem.getPictureUrl()
    );
  }
}
