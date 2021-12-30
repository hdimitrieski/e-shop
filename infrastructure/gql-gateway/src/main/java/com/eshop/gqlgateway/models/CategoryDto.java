package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CategoryDto(
  @JsonProperty("id") UUID id,
  @JsonProperty("name") String name
) {
}
