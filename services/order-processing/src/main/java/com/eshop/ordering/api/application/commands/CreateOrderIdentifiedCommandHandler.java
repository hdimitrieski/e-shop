package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

@Component
public class CreateOrderIdentifiedCommandHandler implements Command.Handler<CreateOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(CreateOrderIdentifiedCommand createOrderIdentifiedCommand) {
    return true; // Ignore duplicate requests for creating an order.
  }
}
