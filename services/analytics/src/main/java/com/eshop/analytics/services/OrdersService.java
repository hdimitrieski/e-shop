package com.eshop.analytics.services;

import com.eshop.analytics.model.Order;

public interface OrdersService {
  Order getSubmittedOrder(Long orderId);

  Order getPaidOrder(Long orderId);
}
