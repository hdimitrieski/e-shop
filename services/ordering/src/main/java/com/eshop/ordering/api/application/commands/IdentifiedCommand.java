package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record IdentifiedCommand<T extends Command<R>, R>(T command, UUID id) implements Command<R> {
}
