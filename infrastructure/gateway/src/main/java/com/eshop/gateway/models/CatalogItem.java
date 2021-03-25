package com.eshop.gateway.models;

public record CatalogItem(
    Long id,
    String name,
    Double price,
    String pictureUrl
) {
}
