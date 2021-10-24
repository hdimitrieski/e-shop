package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.shared.CommandHandler;

@CommandHandler
public class ShipOrderIdentifiedCommandHandler implements Command.Handler<ShipOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(ShipOrderIdentifiedCommand shipOrderCommand) {
    return null; // Ignore duplicate requests for shipping order.
  }
}
