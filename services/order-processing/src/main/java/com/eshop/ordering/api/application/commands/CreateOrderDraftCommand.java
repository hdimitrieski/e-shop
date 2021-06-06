package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.api.application.dtos.OrderDraftDTO;
import com.eshop.ordering.api.application.models.BasketItem;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderDraftCommand(
    @NotEmpty(message = "Buyer id is required")
    @JsonProperty("buyerId")
    String buyerId,
    @JsonProperty("items")
    @Valid
    @NotEmpty(message = "The basket must contain at least one product")
    List<BasketItem> items
) implements Command<OrderDraftDTO> {
}
