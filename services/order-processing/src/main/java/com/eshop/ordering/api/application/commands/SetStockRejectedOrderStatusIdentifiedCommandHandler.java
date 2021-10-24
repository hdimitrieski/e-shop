package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.shared.CommandHandler;

@CommandHandler
public class SetStockRejectedOrderStatusIdentifiedCommandHandler
    implements Command.Handler<SetStockRejectedOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetStockRejectedOrderStatusIdentifiedCommand setStockRejectedOrderStatusIdentifiedCommand) {
    return true;
  }
}
