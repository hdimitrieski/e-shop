package com.eshop.rating.application.queries.ratingsbycatalogitem;

import com.eshop.rating.application.shared.Query;
import com.eshop.rating.model.Rating;

import java.util.UUID;

public record RatingsByCatalogItemIdQuery(UUID catalogItemId) implements Query<Iterable<Rating>> {
}
