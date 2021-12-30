package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.DgsConstants;
import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.api.converters.ToBasketLineItemConverter;
import com.eshop.gqlgateway.api.converters.ToOrderLineItemConverter;
import com.eshop.gqlgateway.services.BasketApiService;
import com.eshop.gqlgateway.services.OrderApiService;
import com.eshop.gqlgateway.types.LineItem;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

/**
 * Datafetcher for {@link LineItem}.
 */
@RequiredArgsConstructor
@DgsComponent
public class LineItemDatafetcher {

  private final OrderApiService orderApiService;
  private final BasketApiService basketApiService;
  private final ToOrderLineItemConverter toOrderLineItemConverter;
  private final ToBasketLineItemConverter toBasketLineItemConverter;

  @DgsQuery
  public DataFetcherResult<LineItem> lineItem(String lineItemId) {
    var nodeId = fromString(lineItemId);

    if (NodeType.OrderLineItem.equals(nodeId.type())) {
      return orderApiService.findOrderItemById(nodeId.id())
        .map(toOrderLineItemConverter::convert)
        .map(lineItem -> DataFetcherResult.<LineItem>newResult()
          .data(lineItem)
          .build()
        ).orElse(null);
    }

    return basketApiService.findBasketItemById(nodeId.id())
      .map(toBasketLineItemConverter::convert)
      .map(lineItem -> DataFetcherResult.<LineItem>newResult()
        .data(lineItem)
        .build()
      ).orElse(null);
  }

  @DgsData(parentType = DgsConstants.LINEITEM.TYPE_NAME)
  public Double price(DataFetchingEnvironment dfe) {
    LineItem lineItem = dfe.getSource();
    lineItem.setPrice(lineItem.getQuantity() * lineItem.getUnitPrice());
    return lineItem.getPrice();
  }

}
