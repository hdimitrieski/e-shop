package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.shared.CommandHandler;

@CommandHandler
public class SetAwaitingValidationIdentifiedOrderStatusCommandHandler
    implements Command.Handler<SetAwaitingValidationOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetAwaitingValidationOrderStatusIdentifiedCommand setAwaitingValidationOrderStatusIdentifiedCommand) {
    return true;
  }
}
