package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.infrastructure.RequestManager;
import org.springframework.stereotype.Component;

@Component
public class SetStockRejectedOrderStatusIdentifiedCommandHandler
    extends IdentifiedCommandHandler<SetStockRejectedOrderStatusCommand, Boolean> {
  public SetStockRejectedOrderStatusIdentifiedCommandHandler(Pipeline pipeline, RequestManager requestManager) {
    super(pipeline, requestManager);
  }

  @Override
  protected Boolean createResultForDuplicateRequest() {
    return true;  // Ignore duplicate requests for processing order.
  }
}
