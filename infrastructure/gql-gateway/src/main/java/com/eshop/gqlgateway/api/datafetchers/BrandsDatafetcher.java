package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.api.converters.ToBrandConverter;
import com.eshop.gqlgateway.services.BrandApiService;
import com.eshop.gqlgateway.types.Brand;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

/**
 * Datafetcher for {@link Brand}.
 */
@RequiredArgsConstructor
@DgsComponent
public class BrandsDatafetcher {
  private final BrandApiService brandApiService;
  private final ToBrandConverter toBrandConverter;

  /**
   * Resolves "brand" field on Query.
   */
  @DgsQuery
  public DataFetcherResult<Brand> brand(String brandId) {
    var nodeId = fromString(brandId);
    return brandApiService.findById(nodeId.id())
      .map(toBrandConverter::convert)
      .map(brand -> DataFetcherResult.<Brand>newResult()
        .data(brand)
        .build()
      ).orElseThrow(DgsEntityNotFoundException::new);
  }

  /**
   * Resolves "brands" field on Query.
   */
  @SuppressWarnings("unused")
  @DgsQuery
  public List<Brand> brands() {
    return brandApiService.findAll().stream()
      .map(toBrandConverter::convert)
      .collect(Collectors.toList());
  }
}
