package com.eshop.gqlgateway.services;

import com.eshop.gqlgateway.models.AddRatingDto;
import com.eshop.gqlgateway.models.RatingDto;

import java.util.List;
import java.util.Set;
import java.util.UUID;

public interface RatingApiService {
  List<RatingDto> findByIds(Set<UUID> ids);

  RatingDto addRating(AddRatingDto rating);
}
