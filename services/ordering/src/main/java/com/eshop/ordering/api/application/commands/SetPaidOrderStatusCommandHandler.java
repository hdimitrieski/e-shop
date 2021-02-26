package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;

@Component
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

    var orderToUpdate = orderRepository.findById(command.orderNumber()).orElse(null);
    if (orderToUpdate == null) {
      return false;
    }

    orderToUpdate.setPaidStatus();
    orderRepository.save(orderToUpdate);
    return true;
  }
}
