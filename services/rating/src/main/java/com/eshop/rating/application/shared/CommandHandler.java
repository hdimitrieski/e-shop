package com.eshop.rating.application.shared;

public interface CommandHandler<R, C extends Command<R>> {
  R handle(C command);

  default boolean matches(C command) {
    final var handlerType = getClass();
    final var commandType = command.getClass();
    return new FirstGenericArgOf(handlerType).isAssignableFrom(commandType);
  }
}
