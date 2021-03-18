package com.eshop.catalog.integrationevents.events;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public record OrderStockItem(Long productId, Integer units) {
}
