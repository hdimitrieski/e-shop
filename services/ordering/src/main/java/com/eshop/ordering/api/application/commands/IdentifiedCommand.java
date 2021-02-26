package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

import java.util.UUID;

public record IdentifiedCommand<T extends Command<R>, R>(T command, UUID id) implements Command<R> {
}

//public class IdentifiedCommand<T, R> : IRequest<R>
//        where T : IRequest<R>
//    {
//public T Command { get; }
//public Guid Id { get; }
//public IdentifiedCommand(T command, Guid id)
//        {
//        Command = command;
//        Id = id;
//        }
//        }