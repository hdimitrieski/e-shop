package com.eshop.gateway.models;

import com.fasterxml.jackson.annotation.JsonProperty;

public record Product(
    @JsonProperty("id") Long id
) {
}
