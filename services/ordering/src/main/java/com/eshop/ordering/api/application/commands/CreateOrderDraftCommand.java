package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.api.application.dtos.OrderDraftDTO;
import com.eshop.ordering.api.application.models.BasketItem;

import java.util.List;

public record CreateOrderDraftCommand(
    String buyerId,
    List<BasketItem> items
) implements Command<OrderDraftDTO> {
}
