package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

public record SetPaidOrderStatusCommand(String orderNumber) implements Command<Boolean> {
}
