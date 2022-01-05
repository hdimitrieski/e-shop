package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.api.models.NodeId;
import com.eshop.gqlgateway.types.Node;
import graphql.execution.DataFetcherResult;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Optional;
import java.util.function.BiFunction;
import java.util.stream.Stream;

@RequiredArgsConstructor
@Getter
public enum NodeTypeResolver {
  Basket(NodeType.Basket, NodeResolver::basket),
  Order(NodeType.Order, NodeResolver::order),
  Product(NodeType.Product, NodeResolver::product),
  BasketLineItem(NodeType.BasketLineItem, NodeResolver::basketLineItem),
  OrderLineItem(NodeType.OrderLineItem, NodeResolver::orderLineItem),
  Brand(NodeType.Brand, NodeResolver::brand),
  Category(NodeType.Category, NodeResolver::category);

  @Getter
  private final NodeType type;
  private final BiFunction<NodeResolver, NodeId, DataFetcherResult<? extends Node>> resolver;

  public static Optional<BiFunction<NodeResolver, NodeId, DataFetcherResult<? extends Node>>> resolverFor(NodeType type) {
    return Stream.of(NodeTypeResolver.values())
      .filter(nodeTypeResolver -> nodeTypeResolver.getType().equals(type))
      .map(NodeTypeResolver::getResolver)
      .findFirst();
  }
}
