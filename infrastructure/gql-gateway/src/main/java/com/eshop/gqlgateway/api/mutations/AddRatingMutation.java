package com.eshop.gqlgateway.api.mutations;

import com.eshop.gqlgateway.models.AddRatingDto;
import com.eshop.gqlgateway.services.RatingApiService;
import com.eshop.gqlgateway.types.AddRatingInput;
import com.eshop.gqlgateway.types.AddRatingPayload;
import com.eshop.gqlgateway.types.Rating;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

@RequiredArgsConstructor
@DgsComponent
public class AddRatingMutation {
  private final RatingApiService ratingApiService;

  @Secured("ROLE_user")
  @DgsMutation
  public AddRatingPayload addRating(@InputArgument AddRatingInput input) {
    final var productId = fromString(input.getProductId()).id();
    final var rating = ratingApiService.addRating(new AddRatingDto(productId, input.getValue()));
    return AddRatingPayload.newBuilder()
      .rating(Rating.newBuilder()
        .value(rating.ratingScale())
        .build())
      .build();
  }
}
