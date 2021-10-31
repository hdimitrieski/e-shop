package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderId;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import com.eshop.ordering.shared.CommandHandler;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.transaction.annotation.Transactional;

@CommandHandler
@RequiredArgsConstructor
public class SetPaidOrderStatusCommandHandler implements Command.Handler<SetPaidOrderStatusCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when Shipment service confirms the payment/
   */
  @SneakyThrows
  @Override
  @Transactional
  public Boolean handle(SetPaidOrderStatusCommand command) {
    // Simulate a work time for validating the payment
    Thread.sleep(10000);

    final var orderToUpdate = orderRepository.findById(OrderId.of(command.orderNumber()))
        .orElse(null);
    if (orderToUpdate == null) {
      return false;
    }

    orderToUpdate.setPaidStatus();
    orderRepository.save(orderToUpdate);
    return true;
  }
}
