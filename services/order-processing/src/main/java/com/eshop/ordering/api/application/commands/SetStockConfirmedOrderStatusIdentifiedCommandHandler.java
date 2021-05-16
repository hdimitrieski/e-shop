package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import org.springframework.stereotype.Component;

@Component
public class SetStockConfirmedOrderStatusIdentifiedCommandHandler
    implements Command.Handler<SetStockConfirmedOrderStatusIdentifiedCommand, Boolean> {
  @Override
  public Boolean handle(SetStockConfirmedOrderStatusIdentifiedCommand setStockConfirmedOrderStatusIdentifiedCommand) {
    return true;
  }
}
