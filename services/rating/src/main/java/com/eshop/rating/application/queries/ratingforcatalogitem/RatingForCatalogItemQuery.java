package com.eshop.rating.application.queries.ratingforcatalogitem;

import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.application.shared.Query;

import java.util.UUID;

public record RatingForCatalogItemQuery(UUID catalogItemId) implements Query<RatingForCatalogItemDto> {
}
