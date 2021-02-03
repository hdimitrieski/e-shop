package com.eshop.basket.model;

import org.springframework.stereotype.Repository;

import java.util.Set;
import java.util.Optional;

public interface BasketRepository {
    Optional<CustomerBasket> getBasket(String customerId);
    Set<String> getUsers();
    CustomerBasket updateBasket(CustomerBasket basket);
    boolean deleteBasket(String id);
}
