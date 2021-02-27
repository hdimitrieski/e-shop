package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

public record ShipOrderCommand(Long orderNumber) implements Command<Boolean> {
}
