package com.eshop.gateway.models;

import java.util.List;

public record UpdateBasketRequest(
    String buyerId,
    List<UpdateBasketRequestItemData> items
) {
}
