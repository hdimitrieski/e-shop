package com.eshop.catalog.application.commands.addbrand;

import com.eshop.catalog.application.commandbus.Command;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;

public record AddBrandCommand(
    @JsonProperty
    @NotNull String name
) implements Command<AddBrandResponse> {
}
