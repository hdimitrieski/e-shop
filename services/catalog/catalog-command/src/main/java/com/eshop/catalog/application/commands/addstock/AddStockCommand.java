package com.eshop.catalog.application.commands.addstock;

import com.eshop.catalog.application.commandbus.Command;
import com.eshop.catalog.application.models.CatalogItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.util.UUID;

public record AddStockCommand(
    @JsonProperty
    @NotNull UUID productId,
    @JsonProperty
    @NotNull @Min(1) Integer quantity
) implements Command<CatalogItemResponse> {
}
