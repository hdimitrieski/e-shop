package com.eshop.catalog.application.commandbus;

public interface CatalogCommandBus {
  <R, C extends Command<R>> R execute(C command);
}
