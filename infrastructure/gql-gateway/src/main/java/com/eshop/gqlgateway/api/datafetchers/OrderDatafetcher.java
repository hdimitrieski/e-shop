package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.DgsConstants;
import com.eshop.gqlgateway.api.context.EshopContext;
import com.eshop.gqlgateway.api.converters.ToUserConverter;
import com.eshop.gqlgateway.api.services.OrderService;
import com.eshop.gqlgateway.services.OrderApiService;
import com.eshop.gqlgateway.types.Order;
import com.eshop.gqlgateway.types.OrderSort;
import com.eshop.gqlgateway.types.User;
import com.netflix.graphql.dgs.*;
import com.netflix.graphql.dgs.context.DgsContext;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import graphql.execution.DataFetcherResult;
import graphql.schema.DataFetchingEnvironment;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;

import java.util.List;
import java.util.stream.Collectors;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

/**
 * Datafetcher for {@link Order}.
 */
@RequiredArgsConstructor
@DgsComponent
public class OrderDatafetcher {

  private final OrderApiService orderApiService;
  private final ToUserConverter toUserConverter;
  private final OrderService orderService;

  /**
   * Resolves "order" field on Query.
   */
  @Secured("ROLE_user")
  @DgsQuery
  public DataFetcherResult<Order> order(@InputArgument String id) {
    var orderId = fromString(id).id();
    return orderApiService.findById(orderId)
      .map(orderService::orderResultFrom)
      .orElseThrow(DgsEntityNotFoundException::new);
  }

  /**
   * Resolves "orders" field on Query.
   */
  @Secured("ROLE_admin")
  @DgsQuery
  public List<DataFetcherResult<Order>> orders(
    @InputArgument Boolean reverse,
    @InputArgument OrderSort orderSort
  ) {
    return orderApiService.allOrders().stream()
      .map(orderService::orderResultFrom)
      .collect(Collectors.toList());
  }

  /**
   * Resolves "user" field on Order.
   * <p>
   * Note:When the admin queries all orders, he would have his user attached to the orders. To fix this, we should
   * resolve the user from the order itself.
   */
  @DgsData(parentType = DgsConstants.ORDER.TYPE_NAME)
  public User user(DgsDataFetchingEnvironment dfe) {
    EshopContext context = DgsContext.getCustomContext(dfe);
    return toUserConverter.convert(context.user());
  }

  /**
   * Resolves "lineItemsQuantity" field on Order.
   */
  @SuppressWarnings("unused")
  @DgsData(parentType = DgsConstants.ORDER.TYPE_NAME)
  public Integer lineItemsQuantity(DataFetchingEnvironment dfe) {
    return orderService.lineItemsQuantity(dfe.getSource());
  }

}
