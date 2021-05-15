package com.eshop.ordering.api.application.behaviours;

import an.awesome.pipelinr.Command;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

//@Component
@RequiredArgsConstructor
@Order(2)
public class IntegrationEventsBehaviour implements Command.Middleware {

  @Override
  public <R, C extends Command<R>> R invoke(C c, Next<R> next) {
    var result = next.invoke();
//    orderingIntegrationEventService.publishEventsThroughEventBus(Thread.currentThread().getId());
    return result;
  }
}
