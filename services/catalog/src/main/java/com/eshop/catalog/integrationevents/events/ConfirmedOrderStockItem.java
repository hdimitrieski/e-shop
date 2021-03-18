package com.eshop.catalog.integrationevents.events;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public record ConfirmedOrderStockItem(Long productId, Boolean hasStock) {
}
