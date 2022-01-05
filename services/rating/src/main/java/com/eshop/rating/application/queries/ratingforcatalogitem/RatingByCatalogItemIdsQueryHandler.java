package com.eshop.rating.application.queries.ratingforcatalogitem;

import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.application.services.CalculateRatingForCatalogItemService;
import com.eshop.rating.application.shared.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class RatingByCatalogItemIdsQueryHandler
  implements QueryHandler<Iterable<RatingForCatalogItemDto>, RatingByCatalogItemIdsQuery> {

  private final CalculateRatingForCatalogItemService calculateRatingForCatalogItemService;

  @Override
  public Iterable<RatingForCatalogItemDto> handle(RatingByCatalogItemIdsQuery query) {
    return query.catalogItemIds().stream()
      .map(calculateRatingForCatalogItemService::calculate)
      .collect(Collectors.toSet());
  }
}
