package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

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
    orderRepository.findById(command.orderNumber())
        .ifPresent(order -> {
          order.setCancelledStatus();
          orderRepository.save(order);
        });

    return true;
  }
}
