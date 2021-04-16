package com.eshop.ordering.api.application.models;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CustomerBasket {
    private String buyerId;
    private List<BasketItem> items;

    public CustomerBasket(String customerId)
    {
        buyerId = customerId;
        items = new ArrayList<>();
    }
}
