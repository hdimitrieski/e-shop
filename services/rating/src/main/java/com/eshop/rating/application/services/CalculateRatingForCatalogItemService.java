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
import java.util.stream.StreamSupport;

@Service
@RequiredArgsConstructor
public class CalculateRatingForCatalogItemService {

  private final RatingRepository ratingRepository;

  private static final Map<RatingScale, Integer> RATING_TO_VALUE;

  static {
    RATING_TO_VALUE = Map.of(
      RatingScale.BAD, 1,
      RatingScale.DECENT, 2,
      RatingScale.GOOD, 3,
      RatingScale.VERY_GOOD, 4,
      RatingScale.EXCELLENT, 5
    );
  }

  public RatingForCatalogItemDto calculate(UUID catalogItemId) {
    int avg = calculateAvgRating(catalogItemId);
    RatingScale rating = avg != 0 ? RATING_TO_VALUE.entrySet().stream().filter(entry -> entry.getValue() == avg).findFirst().get().getKey() : null;
    return RatingForCatalogItemDto.builder()
      .catalogItemId(catalogItemId)
      .ratingScale(rating)
      .build();
  }

  private int calculateAvgRating(UUID catalogItemId) {
    Iterable<Rating> ratingByCatalogItem = ratingRepository.findByCatalogItemId(catalogItemId);
    var ratings = StreamSupport.stream(ratingByCatalogItem.spliterator(), false)
      .map(rating -> RATING_TO_VALUE.get(rating.getRating()))
      .collect(Collectors.toList());
    return ratings.size() != 0 ? ratings.stream().mapToInt(val -> val).sum() / ratings.size() : 0;
  }

}
