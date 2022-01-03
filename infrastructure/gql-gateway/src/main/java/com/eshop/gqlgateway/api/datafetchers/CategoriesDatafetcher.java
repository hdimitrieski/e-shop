package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.api.converters.ToCategoryConverter;
import com.eshop.gqlgateway.services.CategoryApiService;
import com.eshop.gqlgateway.types.Category;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

/**
 * Datafetcher for {@link Category}.
 */
@RequiredArgsConstructor
@DgsComponent
public class CategoriesDatafetcher {
  private final CategoryApiService categoryApiService;
  private final ToCategoryConverter toCategoryConverter;

  /**
   * Resolves "category" field on Query.
   */
  @DgsQuery
  public DataFetcherResult<Category> category(String categoryId) {
    var nodeId = fromString(categoryId);
    return categoryApiService.findById(nodeId.id())
      .map(toCategoryConverter::convert)
      .map(category -> DataFetcherResult.<Category>newResult()
        .data(category)
        .build()
      )
      .orElseThrow(DgsEntityNotFoundException::new);
  }

  /**
   * Resolves "categories" field on Query.
   */
  @SuppressWarnings("unused")
  @DgsQuery
  public List<Category> categories() {
    return categoryApiService.findAll().stream()
      .map(toCategoryConverter::convert)
      .collect(Collectors.toList());
  }
}
