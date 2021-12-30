package com.eshop.basket.services;

import com.eshop.basket.model.BasketCheckout;
import com.eshop.basket.model.CustomerBasket;

import java.util.Optional;
import java.util.UUID;


public interface BasketService {
  Optional<CustomerBasket> findById(UUID basketId);

  CustomerBasket findByCustomerId(String customerId);

  CustomerBasket updateBasket(CustomerBasket basket);

  void checkout(BasketCheckout basketCheckout);

  void delete(UUID basketId);
}
