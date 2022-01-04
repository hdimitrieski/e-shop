package com.eshop.rating.application.commands;

import com.eshop.rating.application.shared.Command;
import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.model.RatingScale;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.NotNull;
import java.util.UUID;

public record AddRatingCommand(
  @JsonProperty
  @NotNull(message = "No catalog item id defined")
  UUID catalogItemId,
  @JsonProperty
  @NotNull(message = "No rating defined")
  RatingScale rating)
  implements Command<RatingForCatalogItemDto> {
}
