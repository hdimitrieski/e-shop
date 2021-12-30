package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public record CatalogItemsPageDto(
  @JsonProperty Integer totalPages,
  @JsonProperty Integer totalElements,
  @JsonProperty Integer numberOfElements,
  @JsonProperty Integer size,
  @JsonProperty Integer number,
  @JsonProperty Boolean hasNext,
  @JsonProperty Boolean hasPrevious,
  @JsonProperty List<CatalogItemDto> content
) {
}
