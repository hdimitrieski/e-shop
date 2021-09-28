package com.eshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.UUID;

public record CatalogItem(
    @JsonProperty("id") UUID id,
    @JsonProperty("name") String name,
    @JsonProperty("price") Double price,
    @JsonProperty("pictureFileName") String pictureFileName
) {
}
