package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.DgsConstants;
import com.eshop.gqlgateway.api.NodeType;
import com.eshop.gqlgateway.api.converters.ToBasketLineItemConverter;
import com.eshop.gqlgateway.api.converters.ToOrderLineItemConverter;
import com.eshop.gqlgateway.services.BasketApiService;
import com.eshop.gqlgateway.services.OrderApiService;
import com.eshop.gqlgateway.types.LineItem;
import com.eshop.gqlgateway.types.Product;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;
import org.springframework.security.access.annotation.Secured;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.eshop.gqlgateway.api.dataloaders.DataLoaders.PRODUCTS;
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

  @Secured("ROLE_user")
  @DgsQuery
  public DataFetcherResult<LineItem> lineItem(String lineItemId) {
    var nodeId = fromString(lineItemId);

    if (NodeType.OrderLineItem.equals(nodeId.type())) {
      return orderApiService.findOrderItemById(nodeId.id())
        .map(orderItemDto -> {
          final var lineItem = toOrderLineItemConverter.convert(orderItemDto);
          return DataFetcherResult.<LineItem>newResult()
            .data(lineItem)
            .localContext(Map.of(orderItemDto.id(), orderItemDto.productId()))
            .build();
        }).orElseThrow(DgsEntityNotFoundException::new);
    }

    return basketApiService.findBasketItemById(nodeId.id())
      .map(basketItemDto -> {
        final var lineItem = toBasketLineItemConverter.convert(basketItemDto);
        return DataFetcherResult.<LineItem>newResult()
          .data(lineItem)
          .localContext(Map.of(basketItemDto.id(), basketItemDto.productId()))
          .build();
      }).orElseThrow(DgsEntityNotFoundException::new);
  }

  /**
   * Resolves "product" field on LineItem.
   * <p>
   * It's invoked for each individual LineItem, so if we load 20 line items, this method will be called 20 times.
   * To avoid the N+1 problem this data-fetcher uses a DataLoader. Although the DataLoader is called for each individual
   * line item ID, it will batch up the actual loading to a single method call to the "load" method in the
   * {@link com.eshop.gqlgateway.api.dataloaders.ProductsDataLoader}.
   */
  @DgsData(parentType = DgsConstants.LINEITEM.TYPE_NAME)
  public CompletableFuture<Product> product(DataFetchingEnvironment dfe) {
    final LineItem lineItem = dfe.getSource();
    final Map<UUID, UUID> productIds = dfe.getLocalContext();
    final var lineItemId = fromString(lineItem.getId()).id();
    DataLoader<UUID, Product> productsLoader = dfe.getDataLoader(PRODUCTS);

    return productsLoader.load(productIds.get(lineItemId));
  }

}
