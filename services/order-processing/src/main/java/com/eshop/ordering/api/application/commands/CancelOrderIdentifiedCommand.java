package com.eshop.ordering.api.application.commands;

import java.util.UUID;

public class CancelOrderIdentifiedCommand extends IdentifiedCommand<CancelOrderCommand, Boolean> {
  public CancelOrderIdentifiedCommand(CancelOrderCommand command, UUID id) {
    super(command, id);
  }
}
