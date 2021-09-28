package com.eshop.catalogquery.application.queries.categorybyid;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.Category;

import java.util.Optional;
import java.util.UUID;

public record CategoryByIdQuery(
    UUID categoryId
) implements Query<Optional<Category>> {
}
