package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

import java.util.List;
import java.util.UUID;

public record SetStockRejectedOrderStatusCommand(
    String orderNumber,
    List<UUID> orderStockItems
) implements Command<Boolean> {
}
