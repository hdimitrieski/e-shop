package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.shared.CommandHandler;

// Use for Idempotency in Command process
@CommandHandler
public class CancelOrderIdentifiedCommandHandler implements Command.Handler<CancelOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(CancelOrderIdentifiedCommand cancelOrderIdentifiedCommand) {
    return true; // Ignore duplicate requests for canceling order.
  }
}
