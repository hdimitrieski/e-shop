package com.eshop.ordering.api.application.commands;

import java.util.UUID;

public class ShipOrderIdentifiedCommand extends IdentifiedCommand<ShipOrderCommand, Boolean> {
  public ShipOrderIdentifiedCommand(ShipOrderCommand command, UUID id) {
    super(command, id);
  }
}
