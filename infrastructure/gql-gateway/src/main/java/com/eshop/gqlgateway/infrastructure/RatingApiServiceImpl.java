package com.eshop.gqlgateway.infrastructure;

import com.eshop.gqlgateway.models.AddRatingDto;
import com.eshop.gqlgateway.models.RatingDto;
import com.eshop.gqlgateway.services.RatingApiService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class RatingApiServiceImpl implements RatingApiService {
  private final RestTemplate ratingsRestTemplate;

  @Override
  public List<RatingDto> findByIds(Set<UUID> ids) {
    var commaSeparatedIds = ids.stream().map(String::valueOf).collect(Collectors.joining(","));
    var response = ratingsRestTemplate.getForEntity(
      "lb://rating/rating/withids/" + commaSeparatedIds,
      RatingDto[].class
    );
    return Arrays.asList(response.getBody());
  }

  @Override
  public RatingDto addRating(AddRatingDto rating) {
    final var response = ratingsRestTemplate.postForEntity(
      "lb://rating/rating/%s".formatted(rating.catalogItemId()),
      rating,
      RatingDto.class
    );
    return response.getBody();
  }
}
