package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.Order;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderId;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.shared.CommandHandler;
import com.eshop.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
public class CancelOrderCommandHandler implements Command.Handler<CancelOrderCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when customer executes cancel order from app.
   */
  @Override
  @Transactional
  public Boolean handle(CancelOrderCommand command) {
    final var order = findOrder(command.orderNumber());
    order.setCancelledStatus();
    orderRepository.save(order);

    return true;
  }

  private Order findOrder(String orderNumber) {
    return orderRepository.findById(OrderId.of(orderNumber))
        .orElseThrow(() -> new NotFoundException("Order %s not found".formatted(orderNumber)));
  }

}
