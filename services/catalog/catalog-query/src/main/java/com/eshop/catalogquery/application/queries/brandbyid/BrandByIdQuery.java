package com.eshop.catalogquery.application.queries.brandbyid;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.model.Brand;

import java.util.Optional;
import java.util.UUID;

public record BrandByIdQuery(
    UUID brandId
) implements Query<Optional<Brand>> {
}
