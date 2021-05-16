package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

@Component
public class SetAwaitingValidationIdentifiedOrderStatusCommandHandler
    implements Command.Handler<SetAwaitingValidationOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetAwaitingValidationOrderStatusIdentifiedCommand setAwaitingValidationOrderStatusIdentifiedCommand) {
    return true;
  }
}
