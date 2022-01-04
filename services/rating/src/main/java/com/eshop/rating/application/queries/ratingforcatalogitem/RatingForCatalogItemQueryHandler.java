package com.eshop.rating.application.queries.ratingforcatalogitem;

import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.application.shared.QueryHandler;
import com.eshop.rating.application.services.CalculateRatingForCatalogItemService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class RatingForCatalogItemQueryHandler implements QueryHandler<RatingForCatalogItemDto, RatingForCatalogItemQuery> {

  private final CalculateRatingForCatalogItemService calculateRatingForCatalogItemService;

  @Override
  public RatingForCatalogItemDto handle(RatingForCatalogItemQuery query) {
    return calculateRatingForCatalogItemService.calculate(query.catalogItemId());
  }
}
