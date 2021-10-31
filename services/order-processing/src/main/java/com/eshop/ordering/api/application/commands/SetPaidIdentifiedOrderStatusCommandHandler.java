package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.shared.CommandHandler;

@CommandHandler
public class SetPaidIdentifiedOrderStatusCommandHandler implements Command.Handler<SetPaidOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetPaidOrderStatusIdentifiedCommand setPaidOrderStatusIdentifiedCommand) {
    return true;
  }
}
