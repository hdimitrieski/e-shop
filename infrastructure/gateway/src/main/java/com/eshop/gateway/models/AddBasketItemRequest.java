package com.eshop.gateway.models;

public record AddBasketItemRequest(Long catalogItemId, String basketId, Integer quantity) {
}
