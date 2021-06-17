package com.eshop.ordering.api.application.integrationevents.events.models;

public record ConfirmedOrderStockItem(Long productId, boolean hasStock) {
}
