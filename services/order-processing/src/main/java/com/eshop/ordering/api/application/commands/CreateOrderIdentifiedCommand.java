package com.eshop.ordering.api.application.commands;

import java.util.UUID;

public class CreateOrderIdentifiedCommand extends IdentifiedCommand<CreateOrderCommand, Boolean> {
  public CreateOrderIdentifiedCommand(CreateOrderCommand command, UUID id) {
    super(command, id);
  }
}
