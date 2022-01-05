package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record RatingDto(
  @JsonProperty("catalogItemId") UUID catalogItemId,
  @JsonProperty("ratingScale") String ratingScale
) {
}
