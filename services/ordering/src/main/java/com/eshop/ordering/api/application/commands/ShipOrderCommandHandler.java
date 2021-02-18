package com.eshop.ordering.api.application.commands;

import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShipOrderCommandHandler {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when administrator executes ship order from app
   */
  public boolean handle(ShipOrderCommand command) {
    var orderToUpdate = orderRepository.get(command.getOrderNumber());
    if (orderToUpdate == null) {
      return false;
    }

    orderToUpdate.setShippedStatus();
    return orderRepository.unitOfWork().saveEntities();
  }
}
