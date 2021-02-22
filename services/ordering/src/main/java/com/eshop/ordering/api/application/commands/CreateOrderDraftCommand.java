package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.api.application.dtos.OrderDraftDTO;
import com.eshop.ordering.api.application.models.BasketItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateOrderDraftCommand implements Command<OrderDraftDTO> {
  private final String buyerId;
  private final List<BasketItem> items;
}
