package com.eshop.gateway.models;

import java.util.List;

public record BasketData(
    String buyerId,
    List<BasketDataItem> items
) {
}
