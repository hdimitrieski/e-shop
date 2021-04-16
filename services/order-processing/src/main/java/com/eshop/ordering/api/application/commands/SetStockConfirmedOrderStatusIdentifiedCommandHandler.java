package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.infrastructure.idempotency.RequestManager;

//@Component
public class SetStockConfirmedOrderStatusIdentifiedCommandHandler
    extends IdentifiedCommandHandler<SetStockConfirmedOrderStatusCommand, Boolean> {
  public SetStockConfirmedOrderStatusIdentifiedCommandHandler(Pipeline pipeline, RequestManager requestManager) {
    super(pipeline, requestManager);
  }

  @Override
  protected Boolean createResultForDuplicateRequest() {
    return true;  // Ignore duplicate requests for processing order.
  }
}
