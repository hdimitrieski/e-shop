package com.eshop.signaler.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record OrderStatus(@JsonProperty("orderId") Long orderId, @JsonProperty("status") String status) {
}
