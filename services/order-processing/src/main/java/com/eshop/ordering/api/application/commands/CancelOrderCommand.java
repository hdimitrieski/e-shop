package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record CancelOrderCommand(
    @JsonProperty("orderNumber")
    @NotNull(message = "No order number found")
    String orderNumber
) implements Command<Boolean> {
}
