package com.eshop.catalogquery.application.queries.allbrands;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.Brand;
import com.eshop.catalogquery.model.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AllBrandsQueryHandler implements QueryHandler<Iterable<Brand>, AllBrandsQuery> {

  private final BrandRepository brandRepository;

  @Override
  public Iterable<Brand> handle(AllBrandsQuery query) {
    return brandRepository.findAll();
  }
}
