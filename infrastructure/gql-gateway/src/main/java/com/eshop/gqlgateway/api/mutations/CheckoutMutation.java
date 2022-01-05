package com.eshop.gqlgateway.api.mutations;

import com.eshop.gqlgateway.api.context.EshopContext;
import com.eshop.gqlgateway.models.BasketCheckoutDto;
import com.eshop.gqlgateway.services.BasketApiService;
import com.eshop.gqlgateway.types.BasketCheckoutInput;
import com.eshop.gqlgateway.types.BasketCheckoutPayload;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.context.DgsContext;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;

@RequiredArgsConstructor
@DgsComponent
public class CheckoutMutation {
  private final BasketApiService basketApiService;

  @SuppressWarnings("unused")
  @Secured("ROLE_user")
  @DgsMutation
  public BasketCheckoutPayload checkout(DataFetchingEnvironment dfe, @InputArgument BasketCheckoutInput input) {
    EshopContext context = DgsContext.getCustomContext(dfe);
    final var checkoutRequest = BasketCheckoutDto.builder()
      .state(input.getAddress().getState())
      .city(input.getAddress().getCity())
      .zipCode(input.getAddress().getZipCode())
      .country(input.getAddress().getCountry())
      .street(input.getAddress().getStreet())
      .cardExpiration(input.getCardDetails().getExpiration())
      .cardHolderName(input.getCardDetails().getCardHolderName())
      .cardNumber(input.getCardDetails().getNumber())
      .cardType(input.getCardDetails().getType())
      .cardSecurityNumber(input.getCardDetails().getSecurityNumber())
      .buyer(context.user().userName())
      .build();

    basketApiService.checkout(checkoutRequest);

    return BasketCheckoutPayload.newBuilder()
      .build();
  }

}
