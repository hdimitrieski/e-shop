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
public class SetStockRejectedOrderStatusCommandHandler implements Command.Handler<SetStockRejectedOrderStatusCommand, Boolean> {
  private final OrderRepository orderRepository;

  /**
   * Handler which processes the command when Stock service rejects the request.
   */
  @SneakyThrows
  @Override
  @Transactional
  public Boolean handle(SetStockRejectedOrderStatusCommand command) {
    // Simulate a work time for rejecting the stock
    Thread.sleep(10000);

    final var orderToUpdate = orderRepository.findById(OrderId.of(command.orderNumber()))
        .orElse(null);
    if (orderToUpdate == null) {
      return false;
    }

    orderToUpdate.setCancelledStatusWhenStockIsRejected(command.orderStockItems());
    orderRepository.save(orderToUpdate);
    return true;
  }
}
