package com.eshop.ordering.api.application.commands;

import java.util.UUID;

public class SetStockRejectedOrderStatusIdentifiedCommand
    extends IdentifiedCommand<SetStockRejectedOrderStatusCommand, Boolean> {
  public SetStockRejectedOrderStatusIdentifiedCommand(SetStockRejectedOrderStatusCommand command, UUID id) {
    super(command, id);
  }
}
