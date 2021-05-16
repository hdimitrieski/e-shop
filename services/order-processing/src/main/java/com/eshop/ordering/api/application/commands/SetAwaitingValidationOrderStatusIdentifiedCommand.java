package com.eshop.ordering.api.application.commands;

import java.util.UUID;

public class SetAwaitingValidationOrderStatusIdentifiedCommand
    extends IdentifiedCommand<SetAwaitingValidationOrderStatusCommand, Boolean> {
  public SetAwaitingValidationOrderStatusIdentifiedCommand(SetAwaitingValidationOrderStatusCommand command, UUID id) {
    super(command, id);
  }
}
