package com.eshop.catalog.application.commands.createproduct;

import com.eshop.catalog.application.commandbus.Command;
import com.eshop.catalog.application.models.CatalogItemResponse;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record CreateProductCommand(
    @JsonProperty
    @NotNull(message = "No product name defined")
    String name,
    @JsonProperty
    String description,
    @JsonProperty
    @NotNull
    Double price,
    @JsonProperty
    String pictureFileName,
    @NotNull
    @JsonProperty
    Integer availableStock,
    @JsonProperty
    @NotNull
    UUID brandId,
    @JsonProperty
    @NotNull
    UUID categoryId
) implements Command<CatalogItemResponse> {
}
