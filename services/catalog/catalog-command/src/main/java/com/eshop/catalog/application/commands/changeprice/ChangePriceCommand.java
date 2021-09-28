package com.eshop.catalog.application.commands.changeprice;

import com.eshop.catalog.application.commandbus.Command;
import com.eshop.catalog.application.models.CatalogItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record ChangePriceCommand(
    @JsonProperty
    @NotNull
    UUID productId,
    @JsonProperty
    @NotNull Double price
) implements Command<CatalogItemResponse> {
}
