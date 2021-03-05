package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.infrastructure.RequestManager;
import org.springframework.stereotype.Component;

// Use for Idempotency in Command process
//@Component
public class CancelOrderIdentifiedCommandHandler extends IdentifiedCommandHandler<CancelOrderCommand, Boolean> {
  public CancelOrderIdentifiedCommandHandler(Pipeline pipeline, RequestManager requestManager) {
    super(pipeline, requestManager);
  }

  @Override
  protected Boolean createResultForDuplicateRequest() {
    return true;  // Ignore duplicate requests for processing order.
  }
}
