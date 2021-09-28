package com.eshop.gateway.models;

import java.util.UUID;

public record AddBasketItemRequest(UUID catalogItemId, String basketId, Integer quantity) {
}
