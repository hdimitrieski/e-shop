package com.eshop.catalogquery.application.queries.catalogitembyid;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.CatalogItem;

import java.util.Optional;
import java.util.UUID;

public record CatalogItemByIdQuery(
    UUID catalogItemId
) implements Query<Optional<CatalogItem>> {
}
