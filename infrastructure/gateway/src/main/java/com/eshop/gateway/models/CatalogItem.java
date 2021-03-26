package com.eshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record CatalogItem(
    @JsonProperty("id") Long id,
    @JsonProperty("name") String name,
    @JsonProperty("price") Double price,
    @JsonProperty("pictureUrl") String pictureUrl
) {
}
