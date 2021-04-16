package com.eshop.ordering.api.application.domaineventhandlers;

public interface DomainEventHandler<E> {
  void handle(E event);
}
