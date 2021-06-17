package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

import java.util.List;

public record SetStockRejectedOrderStatusCommand(
    String orderNumber,
    List<Long> orderStockItems
) implements Command<Boolean> {
}
