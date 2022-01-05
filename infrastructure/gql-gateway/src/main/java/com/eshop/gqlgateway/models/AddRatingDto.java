package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record AddRatingDto(
  @JsonProperty("catalogItemId") UUID catalogItemId,
  @JsonProperty("rating") String rating
) {
}
