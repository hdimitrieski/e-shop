package com.eshop.rating.application.services;

import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.model.RatingOption;
import com.eshop.rating.model.RatingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Map;
import java.util.UUID;

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
    var ratings = ratingRepository.findByCatalogItemId(catalogItemId);
    var ratingValues = new ArrayList<Integer>();
    ratings.forEach(rating -> ratingValues.add(RATING_TO_VALUE.get(rating.getRating())));
    var avg = ratingValues.stream().mapToInt(val -> val).sum() / ratingValues.size();
    return RatingForCatalogItemDto.builder()
      .catalogItemId(catalogItemId)
      .ratingOption(RATING_TO_VALUE.entrySet().stream().filter(entry -> entry.getValue() == avg).findFirst().get().getKey())
      .build();
  }

}
