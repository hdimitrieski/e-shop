package com.eshop.analytics.services;

import com.eshop.analytics.model.Order;

public interface OrdersService {
  Order getSubmittedOrder(String orderId);

  Order getPaidOrder(String orderId);
}
