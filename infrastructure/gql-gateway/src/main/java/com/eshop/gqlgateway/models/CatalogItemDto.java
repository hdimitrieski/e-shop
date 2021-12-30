package com.eshop.gqlgateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CatalogItemDto(
  @JsonProperty("id") UUID id,
  @JsonProperty("name") String name,
  @JsonProperty("description") String description,
  @JsonProperty("price") Double price,
  @JsonProperty("availableStock") Integer availableStock,
  @JsonProperty("category") CategoryDto category,
  @JsonProperty("brand") BrandDto brand,
  @JsonProperty("pictureFileName") String pictureFileName
) {
}
