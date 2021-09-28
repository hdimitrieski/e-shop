package com.eshop.catalog.application.commandbus;

public interface CatalogCommandHandler<R, C extends Command<R>> {
  R handle(C command);
}
