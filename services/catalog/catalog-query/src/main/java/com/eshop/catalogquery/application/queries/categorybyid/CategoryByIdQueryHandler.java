package com.eshop.catalogquery.application.queries.categorybyid;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.Category;
import com.eshop.catalogquery.model.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@RequiredArgsConstructor
@Component
public class CategoryByIdQueryHandler implements QueryHandler<Optional<Category>, CategoryByIdQuery> {

  private final CategoryRepository categoryRepository;

  @Override
  public Optional<Category> handle(CategoryByIdQuery query) {
    return categoryRepository.findById(query.categoryId());
  }
}
