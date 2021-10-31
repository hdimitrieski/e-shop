package com.eshop.ratingquery.application.queries.ratingsbycatalogitem;

import com.eshop.ratingquery.application.querybus.QueryHandler;
import com.eshop.ratingquery.model.Rating;
import com.eshop.ratingquery.model.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class RatingsByCatalogItemIdQueryHandler implements QueryHandler<Iterable<Rating>, RatingsByCatalogItemIdQuery> {

  private final RatingRepository ratingRepository;

  @Override
  public Iterable<Rating> handle(RatingsByCatalogItemIdQuery query) {
    return ratingRepository.findByCatalogItemId(query.catalogItemId());
  }

}
