package com.eshop.gqlgateway.api.dataloaders;

import com.eshop.gqlgateway.api.converters.ToProductConverter;
import com.eshop.gqlgateway.services.CatalogApiService;
import com.eshop.gqlgateway.types.Product;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.RequiredArgsConstructor;
import org.dataloader.BatchLoader;

import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.eshop.gqlgateway.api.dataloaders.DataLoaders.PRODUCTS;

@RequiredArgsConstructor
@DgsDataLoader(name = PRODUCTS)
public class ProductsDataLoader implements BatchLoader<UUID, Product> {
  private final CatalogApiService catalogApiService;
  private final ToProductConverter toProductConverter;

  @Override
  public CompletionStage<List<Product>> load(List<UUID> ids) {
    return CompletableFuture.supplyAsync(() -> catalogApiService.findByIds(ids).stream()
      .map(toProductConverter::convert)
      .collect(Collectors.toList()));
  }
}
