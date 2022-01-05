package com.eshop.gqlgateway.api.dataloaders;

import com.eshop.gqlgateway.models.RatingDto;
import com.eshop.gqlgateway.services.RatingApiService;
import com.eshop.gqlgateway.types.Rating;
import com.netflix.graphql.dgs.DgsDataLoader;
import lombok.RequiredArgsConstructor;
import org.dataloader.MappedBatchLoader;

import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.eshop.gqlgateway.api.dataloaders.DataLoaders.RATINGS;

@RequiredArgsConstructor
@DgsDataLoader(name = RATINGS)
public class RatingsDataLoader implements MappedBatchLoader<UUID, Rating> {
  private final RatingApiService ratingApiService;

  @Override
  public CompletionStage<Map<UUID, Rating>> load(Set<UUID> ids) {
    return CompletableFuture.supplyAsync(() -> ratingApiService.findByIds(ids).stream()
      .collect(Collectors.toMap(RatingDto::catalogItemId, ratingDto -> Rating.newBuilder()
        .value(ratingDto.ratingScale())
        .build()))
    );
  }
}
