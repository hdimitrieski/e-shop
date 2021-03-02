package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.fasterxml.jackson.annotation.JsonProperty;

public record CancelOrderCommand(@JsonProperty("orderNumber") Long orderNumber) implements Command<Boolean> {
}
