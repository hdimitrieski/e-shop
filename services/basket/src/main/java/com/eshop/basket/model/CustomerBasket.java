package com.eshop.basket.model;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomerBasket {
    private final String buyerId;
    private final List<BasketItem> items = new ArrayList<>();

    public CustomerBasket(String customerId) {
        buyerId = customerId;
    }
}
