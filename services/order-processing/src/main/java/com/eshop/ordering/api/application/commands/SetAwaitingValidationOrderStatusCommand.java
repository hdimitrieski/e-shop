package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

public record SetAwaitingValidationOrderStatusCommand(Long orderNumber) implements Command<Boolean> {
}
