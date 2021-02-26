package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

public record CancelOrderCommand(Integer orderNumber) implements Command<Boolean> {
}
