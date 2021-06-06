package com.eshop.ordering.api.infrastructure.middlewares;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;

// Note: its not needed anymore
//@Component
@RequiredArgsConstructor
@Order(2)
public class IntegrationEventPublishingMiddleware implements Command.Middleware {

  @Override
  public <R, C extends Command<R>> R invoke(C c, Next<R> next) {
    var result = next.invoke();
//    orderingIntegrationEventService.publishEventsThroughEventBus(Thread.currentThread().getId());
    return result;
  }
}
