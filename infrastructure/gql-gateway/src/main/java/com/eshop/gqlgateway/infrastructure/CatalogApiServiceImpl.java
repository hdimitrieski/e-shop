package com.eshop.gqlgateway.infrastructure;

import com.eshop.gqlgateway.config.ApiServices;
import com.eshop.gqlgateway.models.CatalogItemDto;
import com.eshop.gqlgateway.models.CatalogItemsPageDto;
import com.eshop.gqlgateway.services.CatalogApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class CatalogApiServiceImpl implements CatalogApiService {
  private final RestTemplate catalogRestTemplate;
  private final ApiServices apiServices;

  @Override
  public Optional<CatalogItemDto> findById(UUID id) {
    return Optional.ofNullable(catalogRestTemplate.getForObject(loadBalanced("/" + id), CatalogItemDto.class));
  }

  @Override
  public List<CatalogItemDto> findByIds(List<UUID> ids) {
    var commaSeparatedIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
    var response = catalogRestTemplate.getForEntity(
      loadBalanced("/withids/" + commaSeparatedIds),
      CatalogItemDto[].class
    );
    return Arrays.asList(response.getBody());
  }

  @Override
  public Optional<CatalogItemsPageDto> findAll(Integer page, Integer pageSize) {
    final var url = loadBalanced("?pageIndex=%s&pageSize=%s".formatted(page, pageSize));
    return Optional.ofNullable(catalogRestTemplate.getForObject(url, CatalogItemsPageDto.class));
  }

  private String loadBalanced(String path) {
    return "lb://%s/catalog/items%s".formatted(apiServices.getCatalogQuery(), path);
  }

}
