package com.eshop.rating.infrastructure;

import com.eshop.rating.application.shared.Command;
import com.eshop.rating.application.shared.CommandBus;
import com.eshop.rating.application.shared.CommandHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class SpringCommandBus implements CommandBus {
  private final ObjectProvider<CommandHandler> commandHandlers;

  @Override
  public <R, C extends Command<R>> R execute(C command) {
    return (R) queryHandlerFor(command).handle(command);
  }

  public CommandHandler queryHandlerFor(Command command) {
    return commandHandlers.stream()
      .filter(commandHandler -> commandHandler.matches(command))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Command handler not found"));
  }
}
