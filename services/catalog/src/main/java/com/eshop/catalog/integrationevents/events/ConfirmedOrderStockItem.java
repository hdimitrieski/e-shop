package com.eshop.catalog.integrationevents.events;

public record ConfirmedOrderStockItem(Long productId, Boolean hasStock) {
}
