package com.eshop.gateway.services;

import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.models.OrderData;

public interface OrderApiClient {
  OrderData getOrderDraftFromBasket(BasketData basket);
}
