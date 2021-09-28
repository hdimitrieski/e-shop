package com.eshop.catalogquery.application.queries.allcategories;

import com.eshop.catalogquery.application.querybus.QueryHandler;
import com.eshop.catalogquery.model.Category;
import com.eshop.catalogquery.model.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AllCategoriesQueryHandler implements QueryHandler<Iterable<Category>, AllCategoryQuery> {

  private final CategoryRepository categoryRepository;

  @Override
  public Iterable<Category> handle(AllCategoryQuery query) {
    return categoryRepository.findAll();
  }
}
