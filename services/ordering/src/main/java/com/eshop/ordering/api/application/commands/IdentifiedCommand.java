package com.eshop.ordering.api.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@AllArgsConstructor
@Getter
public class IdentifiedCommand<T, R> {
    private T command;
    private UUID id;
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