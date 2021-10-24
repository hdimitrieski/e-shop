package com.eshop.ordering.api.application.services;

import com.eshop.ordering.domain.aggregatesmodel.buyer.Buyer;
import com.eshop.ordering.domain.aggregatesmodel.buyer.BuyerRepository;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderId;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.shared.ApplicationService;
import com.eshop.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@ApplicationService
public class OrderApplicationService {
  private final OrderRepository orderRepository;
  private final BuyerRepository buyerRepository;

  public Order findOrder(OrderId orderId) {
    return orderRepository.findById(orderId)
        .orElseThrow(() -> new NotFoundException("Order %s not found".formatted(orderId.toString())));
  }

  public Buyer findBuyerFor(Order order) {
    return order.buyerId()
        .flatMap(buyerRepository::findById)
        .orElseThrow(() -> new NotFoundException("Buyer not found for order %s".formatted(order.getId().getUuid())));
  }
}
