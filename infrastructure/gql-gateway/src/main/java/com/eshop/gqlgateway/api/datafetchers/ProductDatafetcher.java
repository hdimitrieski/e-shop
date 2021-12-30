package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.DgsConstants;
import com.eshop.gqlgateway.api.converters.ToProductConverter;
import com.eshop.gqlgateway.services.CatalogApiService;
import com.eshop.gqlgateway.types.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;

import java.util.Map;
import java.util.UUID;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

/**
 * Datafetcher for {@link Product}.
 */
@RequiredArgsConstructor
@DgsComponent
public class ProductDatafetcher {
  private final CatalogApiService catalogApiService;
  private final ToProductConverter toProductConverter;

  /**
   * Resolves "product" field on Query.
   */
  @DgsQuery
  public DataFetcherResult<Product> product(@InputArgument String id) {
    final var productId = fromString(id).id();
    return catalogApiService.findById(productId)
      .map(toProductConverter::convert)
      .map(product -> DataFetcherResult.<Product>newResult().data(product).build())
      .orElse(null);
  }

  /**
   * Resolves "products" field on Query.
   */
  @SuppressWarnings("unused")
  @DgsQuery
  public ProductsQueryResult products(
    @InputArgument Integer page,
    @InputArgument Integer pageSize,
    @InputArgument String query,
    @InputArgument ProductSort productSort,
    @InputArgument ProductFilter productFilter,
    @InputArgument Boolean reverse
  ) {
    return catalogApiService.findAll(page, pageSize)
      .map(result -> ProductsQueryResult.newBuilder()
        .page(result.number())
        .pageSize(result.size())
        .total(result.totalElements())
        .result(result.content().stream().map(toProductConverter::convert).toList())
        .build())
      .orElse(null);
  }

  /**
   * Resolves "product" field on LineItem.
   * <p>
   * It's invoked for each individual LineItem, so if we load 20 line items, this method will be called 20 times.
   * To avoid the N+1 problem this data-fetcher uses a DataLoader. Although the DataLoader is called for each individual
   * line item ID, it will batch up the actual loading to a single method call to the "load" method in the
   * ProductsDataLoader.
   */
  @DgsData(parentType = DgsConstants.LINEITEM.TYPE_NAME)
  public Product product(DataFetchingEnvironment dfe) {
    final LineItem lineItem = dfe.getSource();
    final Map<UUID, UUID> productIds = dfe.getLocalContext();
    final var lineItemId = fromString(lineItem.getId()).id();

    return catalogApiService.findById(productIds.get(lineItemId))
      .map(toProductConverter::convert)
      .orElse(null);
  }

}
