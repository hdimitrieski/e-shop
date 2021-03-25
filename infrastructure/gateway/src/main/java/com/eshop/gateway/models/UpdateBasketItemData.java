package com.eshop.gateway.models;

public record UpdateBasketItemData(
    String basketItemId,
    Integer newQuantity
) {
}
