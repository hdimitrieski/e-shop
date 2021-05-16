package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

@Component
public class SetPaidIdentifiedOrderStatusCommandHandler implements Command.Handler<SetPaidOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetPaidOrderStatusIdentifiedCommand setPaidOrderStatusIdentifiedCommand) {
    return true;
  }
}
