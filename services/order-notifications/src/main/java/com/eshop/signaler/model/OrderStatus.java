package com.eshop.signaler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderStatus(@JsonProperty("orderId") String orderId, @JsonProperty("status") String status) {
}
