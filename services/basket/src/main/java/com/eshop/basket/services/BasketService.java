package com.eshop.basket.services;

import com.eshop.basket.model.BasketCheckout;
import com.eshop.basket.model.CustomerBasket;


public interface BasketService {
  CustomerBasket getBasketById(String customerId);

  CustomerBasket updateBasket(CustomerBasket basket);

  void checkout(BasketCheckout basketCheckout);

  void deleteBasketForCustomer(String customerId);
}
