package com.eshop.catalogquery.application.queries.brandbyid;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.Brand;
import com.eshop.catalogquery.model.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class BrandByIdQueryHandler implements QueryHandler<Optional<Brand>, BrandByIdQuery> {

  private final BrandRepository brandRepository;

  @Override
  public Optional<Brand> handle(BrandByIdQuery query) {
    return brandRepository.findById(query.brandId());
  }
}
