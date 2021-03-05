package com.eshop.basket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CustomerBasket implements Serializable {
  private String buyerId;
  private List<BasketItem> items = new ArrayList<>();

  public CustomerBasket(String customerId) {
    buyerId = customerId;
  }
}
