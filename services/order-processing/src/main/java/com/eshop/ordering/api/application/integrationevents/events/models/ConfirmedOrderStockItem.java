package com.eshop.ordering.api.application.integrationevents.events.models;

public record ConfirmedOrderStockItem(Integer productId, boolean hasStock) {
}
