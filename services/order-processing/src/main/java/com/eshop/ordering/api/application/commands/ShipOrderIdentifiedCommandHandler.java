package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.infrastructure.idempotency.RequestManager;
import org.springframework.stereotype.Component;

@Component
public class ShipOrderIdentifiedCommandHandler extends IdentifiedCommandHandler<ShipOrderCommand, Boolean> {
  public ShipOrderIdentifiedCommandHandler(Pipeline pipeline, RequestManager requestManager) {
    super(pipeline, requestManager);
  }

  @Override
  protected Boolean createResultForDuplicateRequest() {
    return true;  // Ignore duplicate requests for processing order.
  }
}
