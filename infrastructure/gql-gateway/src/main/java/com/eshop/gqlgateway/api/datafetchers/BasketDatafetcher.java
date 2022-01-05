package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.DgsConstants;
import com.eshop.gqlgateway.api.context.EshopContext;
import com.eshop.gqlgateway.api.converters.ToUserConverter;
import com.eshop.gqlgateway.api.services.BasketService;
import com.eshop.gqlgateway.services.BasketApiService;
import com.eshop.gqlgateway.types.Basket;
import com.eshop.gqlgateway.types.User;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.context.DgsContext;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

/**
 * Datafetcher for {@link Basket}.
 */
@RequiredArgsConstructor
@DgsComponent
public class BasketDatafetcher {

  private final BasketApiService basketApiService;
  private final ToUserConverter toUserConverter;
  private final BasketService basketService;

  /**
   * Resolves "basket" field on Query.
   */
  @Secured("ROLE_user")
  @DgsQuery
  public DataFetcherResult<Basket> basket(String basketId) {
    var nodeId = fromString(basketId);
    return basketApiService.findById(nodeId.id())
      .map(basketService::basketResultFrom)
      .orElseThrow(DgsEntityNotFoundException::new);
  }

  /**
   * Resolves "user" field on Basket.
   */
  @DgsData(parentType = DgsConstants.BASKET.TYPE_NAME)
  public User user(DgsDataFetchingEnvironment dfe) {
    EshopContext context = DgsContext.getCustomContext(dfe);
    return toUserConverter.convert(context.user());
  }

  /**
   * Resolves "lineItemQuantity" field on Basket.
   */
  @SuppressWarnings("unused")
  @DgsData(parentType = DgsConstants.BASKET.TYPE_NAME)
  public Integer lineItemsQuantity(DataFetchingEnvironment dfe) {
    return basketService.lineItemsQuantity(dfe.getSource());
  }

  /**
   * Resolves "totalPrice" field on Basket.
   */
  @SuppressWarnings("unused")
  @DgsData(parentType = DgsConstants.BASKET.TYPE_NAME)
  public Double totalPrice(DataFetchingEnvironment dfe) {
    return basketService.totalPrice(dfe.getSource());
  }

}
