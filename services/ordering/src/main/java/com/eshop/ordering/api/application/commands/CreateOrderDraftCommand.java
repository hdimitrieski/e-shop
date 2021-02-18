package com.eshop.ordering.api.application.commands;

import com.eshop.ordering.api.application.models.BasketItem;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@AllArgsConstructor
@Getter
public class CreateOrderDraftCommand {
    private final String buyerId;
    private final List<BasketItem> items;
}
