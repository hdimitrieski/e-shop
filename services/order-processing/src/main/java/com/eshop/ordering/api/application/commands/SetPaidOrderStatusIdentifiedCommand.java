package com.eshop.ordering.api.application.commands;

import java.util.UUID;

public class SetPaidOrderStatusIdentifiedCommand extends IdentifiedCommand<SetPaidOrderStatusCommand, Boolean> {
  public SetPaidOrderStatusIdentifiedCommand(SetPaidOrderStatusCommand command, UUID id) {
    super(command, id);
  }
}
