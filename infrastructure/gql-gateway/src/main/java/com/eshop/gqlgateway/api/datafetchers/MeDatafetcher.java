package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.DgsConstants;
import com.eshop.gqlgateway.api.context.EshopContext;
import com.eshop.gqlgateway.api.converters.ToUserConverter;
import com.eshop.gqlgateway.api.services.BasketService;
import com.eshop.gqlgateway.api.services.OrderService;
import com.eshop.gqlgateway.services.BasketApiService;
import com.eshop.gqlgateway.services.OrderApiService;
import com.eshop.gqlgateway.types.*;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import com.netflix.graphql.dgs.context.DgsContext;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Datafetcher for {@link User}.
 */
@RequiredArgsConstructor
@DgsComponent
public class MeDatafetcher {
  private final BasketApiService basketApiService;
  private final OrderApiService orderApiService;
  private final ToUserConverter toUserConverter;
  private final OrderService orderService;
  private final BasketService basketService;

  /**
   * Resolves "me" field on Query.
   */
  @Secured("ROLE_user")
  @DgsQuery
  public Me me(DataFetchingEnvironment dfe) {
    EshopContext context = DgsContext.getCustomContext(dfe);
    return Me.newBuilder()
      .user(toUserConverter.convert(context.user()))
      .build();
  }

  /**
   * Resolves "basket" field on User.
   */
  @DgsData(parentType = DgsConstants.ME.TYPE_NAME)
  public DataFetcherResult<Basket> basket(DataFetchingEnvironment dfe) {
    EshopContext context = DgsContext.getCustomContext(dfe);
    return basketApiService.findByUserId(context.user().userName())
      .map(basketService::basketResultFrom)
      .orElseThrow(DgsEntityNotFoundException::new);
  }

  /**
   * Resolves "orders" field on User.
   */
  @DgsData(parentType = DgsConstants.ME.TYPE_NAME)
  public List<DataFetcherResult<Order>> orders(
    @InputArgument Boolean reverse,
    @InputArgument OrderSort orderSort
  ) {
    return orderApiService.userOrders().stream()
      .map(orderService::orderResultFrom)
      .collect(Collectors.toList());
  }

}
