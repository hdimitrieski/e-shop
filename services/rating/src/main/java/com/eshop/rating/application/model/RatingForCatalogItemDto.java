package com.eshop.rating.application.model;

import com.eshop.rating.model.RatingScale;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.util.UUID;

/**
 * Represents the average rating option for a given catalog item.
 */
@AllArgsConstructor
@Builder
public class RatingForCatalogItemDto {
  private UUID catalogItemId;
  private RatingScale ratingScale;
}
