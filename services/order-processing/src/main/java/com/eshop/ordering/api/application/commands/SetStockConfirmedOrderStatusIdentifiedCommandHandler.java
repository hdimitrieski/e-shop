package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.shared.CommandHandler;

@CommandHandler
public class SetStockConfirmedOrderStatusIdentifiedCommandHandler
    implements Command.Handler<SetStockConfirmedOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetStockConfirmedOrderStatusIdentifiedCommand setStockConfirmedOrderStatusIdentifiedCommand) {
    return true;
  }
}
