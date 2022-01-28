package com.eshop.gqlgateway.api.mutations;

import com.eshop.gqlgateway.api.converters.ToBasketConverter;
import com.eshop.gqlgateway.models.BasketDto;
import com.eshop.gqlgateway.models.BasketItemDto;
import com.eshop.gqlgateway.services.BasketApiService;
import com.eshop.gqlgateway.types.BasketRemoveQuantityInput;
import com.eshop.gqlgateway.types.BasketRemoveQuantityPayload;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;

import java.util.Optional;
import java.util.UUID;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

@RequiredArgsConstructor
@DgsComponent
public class RemoveQuantityMutation {
  private final BasketApiService basketApiService;
  private final ToBasketConverter toBasketConverter;

  @SuppressWarnings("unused")
  @Secured("ROLE_user")
  @DgsMutation
  public BasketRemoveQuantityPayload removeQuantity(@InputArgument BasketRemoveQuantityInput input) {
    final var basketId = fromString(input.getBasketId()).id();
    final var lineItemId = fromString(input.getLineItemId()).id();

    return basketApiService.findById(basketId)
      .flatMap(basket -> removeQuantity(basket, lineItemId))
      .flatMap(basketApiService::update)
      .map(toBasketConverter::convert)
      .map(updatedBasket -> BasketRemoveQuantityPayload.newBuilder()
        .basket(updatedBasket)
        .build())
      .orElseThrow(DgsEntityNotFoundException::new);
  }

  private Optional<BasketDto> removeQuantity(BasketDto basket, UUID lineItemId) {
    final var basketItem = basket.items().stream()
      .filter(item -> item.id().equals(lineItemId))
      .findFirst()
      .orElseThrow(DgsEntityNotFoundException::new);

    if (basketItem.quantity() > 1) {
      basket.items().set(
        basket.items().indexOf(basketItem),
        updateBasketItem(basketItem)
      );
    } else {
      basket.items().remove(basketItem);
    }

    return basketApiService.update(basket);
  }

  private BasketItemDto updateBasketItem(BasketItemDto basketItem) {
    return new BasketItemDto(
      basketItem.id(),
      basketItem.productId(),
      basketItem.productName(),
      basketItem.unitPrice(),
      basketItem.oldUnitPrice(),
      basketItem.quantity() - 1,
      basketItem.pictureUrl()
    );
  }

}
