package com.eshop.gateway.models;

import java.util.List;

public record UpdateBasketItemRequest(
    String basketId,
    List<UpdateBasketItemData> updates
) {
}
