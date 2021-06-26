package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

@Component
public class ShipOrderIdentifiedCommandHandler implements Command.Handler<ShipOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(ShipOrderIdentifiedCommand shipOrderCommand) {
    return null; // Ignore duplicate requests for shipping order.
  }
}
