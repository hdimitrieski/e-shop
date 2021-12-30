package com.eshop.gqlgateway.api;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;

import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum NodeType {
  Basket("Basket"),
  Order("Order"),
  Product("Product"),
  User("User"),
  BasketLineItem("BasketLineItem"),
  OrderLineItem("OrderLineItem"),
  Brand("Brand"),
  Category("Category");

  @Getter
  private final String name;

  public static NodeType of(@NonNull String nodeType) {
    return Stream.of(values()).filter(n -> n.getName().equals(nodeType))
      .findFirst()
      .orElseThrow(() -> new IllegalArgumentException("Invalid node type: %s".formatted(nodeType)));
  }

}
