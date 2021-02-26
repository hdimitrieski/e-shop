package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;

import java.util.List;

public record SetStockRejectedOrderStatusCommand(
    Integer orderNumber,
    List<Integer> orderStockItems) implements Command<Boolean> {
}
