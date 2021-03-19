package com.eshop.basket.model;

import java.util.Optional;
import java.util.Set;

public interface BasketRepository {
  Optional<CustomerBasket> getBasket(String customerId);

  Set<String> getUsers();

  CustomerBasket updateBasket(CustomerBasket basket);

  boolean deleteBasket(String id);
}
