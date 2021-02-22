package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class SetStockRejectedOrderStatusCommand implements Command<Boolean> {
  private final Integer orderNumber;
  private final List<Integer> OrderStockItems;
}
