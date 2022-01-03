package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.api.converters.ToProductConverter;
import com.eshop.gqlgateway.services.CatalogApiService;
import com.eshop.gqlgateway.types.Product;
import com.eshop.gqlgateway.types.ProductFilter;
import com.eshop.gqlgateway.types.ProductSort;
import com.eshop.gqlgateway.types.ProductsQueryResult;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;

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
      .orElseThrow(DgsEntityNotFoundException::new);
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
      .orElseThrow(DgsEntityNotFoundException::new);
  }

}
