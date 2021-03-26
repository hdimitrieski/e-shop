package com.eshop.gateway.services;

import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.models.OrderData;
import reactor.core.publisher.Mono;

public interface OrderingApiService {
  Mono<OrderData> getOrderDraft(BasketData basket);
}
