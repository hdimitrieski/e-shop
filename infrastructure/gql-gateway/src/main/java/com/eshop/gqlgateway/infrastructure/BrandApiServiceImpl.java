package com.eshop.gqlgateway.infrastructure;

import com.eshop.gqlgateway.models.BrandDto;
import com.eshop.gqlgateway.services.BrandApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class BrandApiServiceImpl implements BrandApiService {
  private final RestTemplate catalogRestTemplate;

  @Override
  public Optional<BrandDto> findById(UUID id) {
    return Optional.ofNullable(
      catalogRestTemplate.getForObject("lb://catalog-query/catalog/brands/%s".formatted(id), BrandDto.class));
  }

  @Override
  public List<BrandDto> findAll() {
    var response = catalogRestTemplate.getForEntity("lb://catalog-query/catalog/brands", BrandDto[].class);
    return Arrays.asList(response.getBody());
  }
}
