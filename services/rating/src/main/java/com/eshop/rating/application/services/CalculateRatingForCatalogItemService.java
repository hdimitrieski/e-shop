package com.eshop.rating.application.services;

import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.model.Rating;
import com.eshop.rating.model.RatingOption;
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

  private static final Map<RatingOption, Integer> RATING_TO_VALUE;

  static {
    RATING_TO_VALUE = Map.of(
      RatingOption.BAD, 1,
      RatingOption.DECENT, 2,
      RatingOption.GOOD, 3,
      RatingOption.VERY_GOOD, 4,
      RatingOption.EXCELLENT, 5
    );
  }

  public RatingForCatalogItemDto calculate(UUID catalogItemId) {
    int avg = calculateAvgRating(catalogItemId);
    RatingOption rating = avg != 0 ? RATING_TO_VALUE.entrySet().stream().filter(entry -> entry.getValue() == avg).findFirst().get().getKey() : null;
    return RatingForCatalogItemDto.builder()
      .catalogItemId(catalogItemId)
      .ratingOption(rating)
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
