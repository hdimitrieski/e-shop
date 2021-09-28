package com.eshop.catalog.infrastructure.commandbus;

import com.eshop.catalog.application.commandbus.CatalogCommandBus;
import com.eshop.catalog.application.commandbus.Command;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AxonCommandBus implements CatalogCommandBus {
  private final CommandGateway commandGateway;

  @Override
  public <R, C extends Command<R>> R execute(C command) {
    return commandGateway.sendAndWait(command);
  }
}
