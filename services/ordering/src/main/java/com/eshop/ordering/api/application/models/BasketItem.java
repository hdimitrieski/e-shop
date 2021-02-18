package com.eshop.ordering.api.application.models;

import com.eshop.ordering.api.application.dtos.OrderItemDTO;
import lombok.Getter;

@Getter
public class BasketItem {
    private String id;
    private Integer productId;
    private String productName;
    private Double unitPrice;
    private Double oldUnitPrice;
    private Integer quantity;
    private String pictureUrl;

    public static OrderItemDTO toOrderItemDTO(BasketItem item)
    {
        return new OrderItemDTO(
            item.getProductId(),
            item.getProductName(),
            item.getUnitPrice(),
            0D,
            item.getQuantity(),
            item.getPictureUrl()
        );
    }
}
