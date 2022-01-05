package com.eshop.rating.application.services;

import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.model.Rating;
import com.eshop.rating.model.RatingScale;
import com.eshop.rating.model.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CalculateRatingForCatalogItemService {

  private final RatingRepository ratingRepository;

  public RatingForCatalogItemDto calculate(UUID catalogItemId) {
    int avg = calculateAvgRating(catalogItemId);
    RatingScale rating = avg != 0 ? Stream.of(RatingScale.values()).filter(ratingScale -> ratingScale.getScale() == avg).findFirst().get() : null;
    return RatingForCatalogItemDto.builder()
      .catalogItemId(catalogItemId)
      .ratingScale(rating)
      .build();
  }

  private int calculateAvgRating(UUID catalogItemId) {
    Iterable<Rating> ratingByCatalogItem = ratingRepository.findByCatalogItemId(catalogItemId);
    var ratings = StreamSupport.stream(ratingByCatalogItem.spliterator(), false)
      .map(rating -> rating.getRating().getScale())
      .collect(Collectors.toList());
    return ratings.size() != 0 ? ratings.stream().mapToInt(val -> val).sum() / ratings.size() : 0;
  }

}
