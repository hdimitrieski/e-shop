package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
@RequiredArgsConstructor
public class ShipOrderCommandHandler implements Command.Handler<ShipOrderCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when administrator executes ship order from app
   */
  @Transactional
  @Override
  public Boolean handle(ShipOrderCommand command) {
    orderRepository.findById(command.getOrderNumber())
        .ifPresent(Order::setShippedStatus);
    return true;
//    return orderRepository.unitOfWork().saveEntities();
  }
}
