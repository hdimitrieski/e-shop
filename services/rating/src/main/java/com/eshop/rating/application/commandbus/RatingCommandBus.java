package com.eshop.rating.application.commandbus;

public interface RatingCommandBus {
  <R, C extends Command<R>> R execute(C command);
}
