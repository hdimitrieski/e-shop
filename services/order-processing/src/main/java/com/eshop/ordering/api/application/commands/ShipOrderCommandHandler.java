package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    var order = findOrder(command.orderNumber());
    order.setShippedStatus();
    return true;
  }

  private Order findOrder(Long orderNumber) {
    return orderRepository.findById(orderNumber)
        .orElseThrow(() -> new NotFoundException("Order %s not found".formatted(orderNumber)));
  }
}
