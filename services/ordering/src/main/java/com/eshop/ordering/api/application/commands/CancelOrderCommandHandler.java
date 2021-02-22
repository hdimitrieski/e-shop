package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class CancelOrderCommandHandler implements Command.Handler<CancelOrderCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when customer executes cancel order from app.
   */
  @Override
  @Transactional
  public Boolean handle(CancelOrderCommand command) {
//    var orderToUpdate = orderRepository.findById(command.getOrderNumber());

    orderRepository.findById(command.getOrderNumber())
        .ifPresent(Order::setCancelledStatus);

//    if (orderToUpdate == null) {
//      return false;
//    }
//    orderToUpdate.setCancelledStatus();
    return true;
//        return orderRepository.unitOfWork().saveEntities();
  }
}
