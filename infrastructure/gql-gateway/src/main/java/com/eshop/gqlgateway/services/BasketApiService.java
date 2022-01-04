package com.eshop.gqlgateway.services;

import com.eshop.gqlgateway.models.BasketCheckoutDto;
import com.eshop.gqlgateway.models.BasketDto;
import com.eshop.gqlgateway.models.BasketItemDto;

import java.util.Optional;
import java.util.UUID;

public interface BasketApiService {
  Optional<BasketDto> findByUserId(String userId);

  Optional<BasketDto> findById(UUID id);

  Optional<BasketItemDto> findBasketItemById(UUID id);

  Optional<BasketDto> update(BasketDto basket);

  void checkout(BasketCheckoutDto basketCheckout);
}
