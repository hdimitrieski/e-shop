package com.eshop.rating.application.queries.ratingsbycatalogitem;

import com.eshop.rating.application.shared.QueryHandler;
import com.eshop.rating.model.Rating;
import com.eshop.rating.model.RatingRepository;
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
