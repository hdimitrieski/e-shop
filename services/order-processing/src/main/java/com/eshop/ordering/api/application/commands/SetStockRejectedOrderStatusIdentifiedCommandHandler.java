package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

@Component
public class SetStockRejectedOrderStatusIdentifiedCommandHandler
    implements Command.Handler<SetStockRejectedOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetStockRejectedOrderStatusIdentifiedCommand setStockRejectedOrderStatusIdentifiedCommand) {
    return true;
  }
}
