package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.infrastructure.idempotency.RequestManager;

//@Component
public class SetAwaitingValidationIdentifiedOrderStatusCommandHandler
    extends IdentifiedCommandHandler<SetAwaitingValidationOrderStatusCommand, Boolean> {
  public SetAwaitingValidationIdentifiedOrderStatusCommandHandler(Pipeline pipeline, RequestManager requestManager) {
    super(pipeline, requestManager);
  }

  @Override
  protected Boolean createResultForDuplicateRequest() {
    return true;  // Ignore duplicate requests for processing order.
  }
}
