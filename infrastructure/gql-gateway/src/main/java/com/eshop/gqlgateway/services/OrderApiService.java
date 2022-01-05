package com.eshop.gqlgateway.services;

import com.eshop.gqlgateway.models.OrderDto;
import com.eshop.gqlgateway.models.OrderItemDto;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrderApiService {
  Optional<OrderDto> findById(UUID id);

  List<OrderDto> userOrders();

  List<OrderDto> allOrders();

  Optional<OrderItemDto> findOrderItemById(UUID id);
}
