package com.eshop.basket.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Min;
import java.math.BigDecimal;

@Getter
public class BasketItem {
    private String id;
    private Long productId;
    private String productName;
    @Setter
    private BigDecimal unitPrice;
    @Setter
    private BigDecimal oldUnitPrice;
    @Min(value = 1, message = "Invalid number of units")
    private Integer quantity;
    private String pictureUrl;
}
