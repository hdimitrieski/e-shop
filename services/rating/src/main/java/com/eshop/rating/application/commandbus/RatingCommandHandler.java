package com.eshop.rating.application.commandbus;

public interface RatingCommandHandler<R, C extends Command<R>> {
  R handle(C command);
}
