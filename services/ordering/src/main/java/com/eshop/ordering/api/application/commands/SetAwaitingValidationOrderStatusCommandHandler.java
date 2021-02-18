package com.eshop.ordering.api.application.commands;

import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class SetAwaitingValidationOrderStatusCommandHandler {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when graceperiod has finished
   */
  public boolean handle(CancelOrderCommand command) {
    var orderToUpdate = orderRepository.get(command.getOrderNumber());
    if (orderToUpdate == null) {
      return false;
    }
    orderToUpdate.setAwaitingValidationStatus();
    return orderRepository.unitOfWork().saveEntities();
  }
}
