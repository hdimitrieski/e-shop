package com.eshop.ordering.api.application.models;

import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Getter
public class CustomerBasket {
    private final String buyerId;
    private final List<BasketItem> items;

    public CustomerBasket(String customerId)
    {
        buyerId = customerId;
        items = new ArrayList<>();
    }
}
