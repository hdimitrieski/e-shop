package com.eshop.basket.model;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor
public class CustomerBasket implements Serializable {
  @NotEmpty(message = "Buyer id is required")
  private String buyerId;
  private String status = "NEW";
  @NotEmpty(message = "The basket must contain at least one item")
  @Valid
  private final List<BasketItem> items = new ArrayList<>();

  public CustomerBasket(String customerId) {
    buyerId = customerId;
  }

  public void changeStatusTo(String status) {
    this.status = status;
  }
}
