package com.eshop.ordering.api.application.commands;

import java.util.UUID;

public class SetStockConfirmedOrderStatusIdentifiedCommand extends
    IdentifiedCommand<SetStockConfirmedOrderStatusCommand, Boolean> {
  public SetStockConfirmedOrderStatusIdentifiedCommand(SetStockConfirmedOrderStatusCommand command, UUID id) {
    super(command, id);
  }
}
