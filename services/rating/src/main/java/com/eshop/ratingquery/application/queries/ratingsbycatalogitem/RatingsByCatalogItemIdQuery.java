package com.eshop.ratingquery.application.queries.ratingsbycatalogitem;

import com.eshop.ratingquery.application.querybus.Query;
import com.eshop.ratingquery.model.Rating;

import java.util.UUID;

public record RatingsByCatalogItemIdQuery(UUID catalogItemId) implements Query<Iterable<Rating>> {
}
