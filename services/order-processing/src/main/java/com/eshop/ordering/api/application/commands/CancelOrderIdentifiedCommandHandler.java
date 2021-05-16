package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

// Use for Idempotency in Command process
@Component
public class CancelOrderIdentifiedCommandHandler implements Command.Handler<CancelOrderIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(CancelOrderIdentifiedCommand cancelOrderIdentifiedCommand) {
    return true; // Ignore duplicate requests for canceling order.
  }
}
