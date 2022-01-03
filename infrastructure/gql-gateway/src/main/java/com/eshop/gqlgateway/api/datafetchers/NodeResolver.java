package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.api.models.NodeId;
import com.eshop.gqlgateway.types.*;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class NodeResolver {
  private final BasketDatafetcher basketDatafetcher;
  private final OrderDatafetcher orderDatafetcher;
  private final BrandsDatafetcher brandsDatafetcher;
  private final LineItemDatafetcher lineItemDatafetcher;
  private final ProductDatafetcher productDatafetcher;
  private final CategoriesDatafetcher categoriesDatafetcher;

  public DataFetcherResult<Basket> basket(NodeId basketId) {
    return basketDatafetcher.basket(basketId.encodedId());
  }

  public DataFetcherResult<Order> order(NodeId orderId) {
    return orderDatafetcher.order(orderId.encodedId());
  }

  public DataFetcherResult<LineItem> basketLineItem(NodeId lineItemId) {
    return lineItemDatafetcher.lineItem(lineItemId.encodedId());
  }

  public DataFetcherResult<LineItem> orderLineItem(NodeId lineItemId) {
    return lineItemDatafetcher.lineItem(lineItemId.encodedId());
  }

  public DataFetcherResult<Product> product(NodeId productId) {
    return productDatafetcher.product(productId.encodedId());
  }

  public DataFetcherResult<Category> category(NodeId categoryId) {
    return categoriesDatafetcher.category(categoryId.encodedId());
  }

  public DataFetcherResult<Brand> brand(NodeId brandId) {
    return brandsDatafetcher.brand(brandId.encodedId());
  }

  public DataFetcherResult<? extends Node> resolve(NodeId nodeId) {
    return NodeTypeResolver.resolverFor(nodeId.type())
      .map(resolver -> resolver.apply(this, nodeId))
      .orElseThrow(() -> new DgsEntityNotFoundException("Invalid node id"));
  }

}
