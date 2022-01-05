package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.DgsConstants;
import com.eshop.gqlgateway.api.converters.ToProductConverter;
import com.eshop.gqlgateway.services.CatalogApiService;
import com.eshop.gqlgateway.types.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.dataloader.DataLoader;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import static com.eshop.gqlgateway.api.dataloaders.DataLoaders.RATINGS;
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

  @DgsData(parentType = DgsConstants.PRODUCT.TYPE_NAME)
  public CompletableFuture<Rating> rating(DataFetchingEnvironment dfe) {
    final Product product = dfe.getSource();
    final var productId = fromString(product.getId()).id();
    DataLoader<UUID, Rating> ratingsLoader = dfe.getDataLoader(RATINGS);

    return ratingsLoader.load(productId);
  }
}
