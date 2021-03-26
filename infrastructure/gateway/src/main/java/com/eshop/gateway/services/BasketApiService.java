package com.eshop.gateway.services;

import com.eshop.gateway.models.BasketData;
import reactor.core.publisher.Mono;

public interface BasketApiService {
  Mono<BasketData> getById(String id);

  Mono<BasketData> update(BasketData currentBasket);
}
