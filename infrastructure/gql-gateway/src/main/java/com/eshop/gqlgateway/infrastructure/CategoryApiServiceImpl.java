package com.eshop.gqlgateway.infrastructure;

import com.eshop.gqlgateway.models.CategoryDto;
import com.eshop.gqlgateway.services.CategoryApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class CategoryApiServiceImpl implements CategoryApiService {
  private final RestTemplate catalogRestTemplate;

  @Override
  public Optional<CategoryDto> findById(UUID id) {
    return Optional.ofNullable(
      catalogRestTemplate.getForObject("lb://catalog-query/catalog/categories/%s".formatted(id), CategoryDto.class));
  }

  @Override
  public List<CategoryDto> findAll() {
    var response = catalogRestTemplate.getForEntity("lb://catalog-query/catalog/categories", CategoryDto[].class);
    return Arrays.asList(response.getBody());
  }
}
