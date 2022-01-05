package com.eshop.gqlgateway.api.mutations;

import com.eshop.gqlgateway.api.converters.ToBasketConverter;
import com.eshop.gqlgateway.models.BasketDto;
import com.eshop.gqlgateway.models.BasketItemDto;
import com.eshop.gqlgateway.models.CatalogItemDto;
import com.eshop.gqlgateway.services.BasketApiService;
import com.eshop.gqlgateway.services.CatalogApiService;
import com.eshop.gqlgateway.types.AddToBasketInput;
import com.eshop.gqlgateway.types.AddToBasketPayload;
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
public class AddToBasketMutation {
  private final BasketApiService basketApiService;
  private final CatalogApiService catalogApiService;
  private final ToBasketConverter toBasketConverter;

  @SuppressWarnings("unused")
  @Secured("ROLE_user")
  @DgsMutation
  public AddToBasketPayload addToBasket(@InputArgument AddToBasketInput input) {
    final var basketId = fromString(input.getBasketId()).id();
    final var lineItemId = fromString(input.getLineItemId()).id();

    return basketApiService.findById(basketId)
      .flatMap(basket -> addToBasket(basket, lineItemId))
      .map(toBasketConverter::convert)
      .map(updatedBasket -> AddToBasketPayload.newBuilder()
        .basket(updatedBasket)
        .build())
      .orElseThrow(DgsEntityNotFoundException::new);
  }

  private Optional<BasketDto> addToBasket(BasketDto basket, UUID lineItemId) {
    return catalogApiService.findById(lineItemId).flatMap(catalogItem -> {
      var updatedBasket = updateBasket(basket, catalogItem);
      return basketApiService.update(updatedBasket);
    });
  }

  private BasketDto updateBasket(BasketDto basket, CatalogItemDto catalogItem) {
    var existingProduct = basket.items().stream()
      .filter(item -> item.productId().equals(catalogItem.id()))
      .findFirst()
      .orElse(null);

    if (existingProduct != null) {
      basket.items().remove(existingProduct);
      basket.items().add(updateBasketItem(existingProduct));
    } else {
      basket.items().add(createNewBasketItem(catalogItem));
    }

    return basket;
  }

  private BasketItemDto updateBasketItem(BasketItemDto basketItem) {
    return new BasketItemDto(
      basketItem.id(),
      basketItem.productId(),
      basketItem.productName(),
      basketItem.unitPrice(),
      basketItem.oldUnitPrice(),
      basketItem.quantity() + 1,
      basketItem.pictureUrl()
    );
  }

  private BasketItemDto createNewBasketItem(CatalogItemDto catalogItem) {
    return new BasketItemDto(
      UUID.randomUUID(),
      catalogItem.id(),
      catalogItem.name(),
      catalogItem.price(),
      catalogItem.price(),
      1,
      catalogItem.pictureFileName()
    );
  }

}
