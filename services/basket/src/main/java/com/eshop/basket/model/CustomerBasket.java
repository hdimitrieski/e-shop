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
  private String status = "NEW";
  private final List<BasketItem> items = new ArrayList<>();

  public CustomerBasket(String customerId) {
    buyerId = customerId;
  }

  public void changeStatusTo(String status) {
    this.status = status;
  }
}
