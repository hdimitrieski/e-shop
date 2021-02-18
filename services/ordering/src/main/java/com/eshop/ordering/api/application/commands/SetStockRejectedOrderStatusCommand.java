package com.eshop.ordering.api.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SetStockRejectedOrderStatusCommand {
    private final Integer orderNumber;
    private final List<Integer> OrderStockItems;
}
