package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class IdentifiedCommand<T extends Command<R>, R> implements Command<R> {
  private final T command;
  private final UUID id;
}
