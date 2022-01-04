package com.eshop.rating.application.shared;

public interface CommandBus {
  <R, C extends Command<R>> R execute(C command);
}
