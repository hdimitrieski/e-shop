package com.eshop.gqlgateway.infrastructure;

import com.eshop.gqlgateway.models.OrderDto;
import com.eshop.gqlgateway.models.OrderItemDto;
import com.eshop.gqlgateway.services.OrderApiService;
import com.netflix.graphql.dgs.exceptions.DgsBadRequestException;
import com.netflix.graphql.dgs.exceptions.DgsEntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OrderApiServiceImpl implements OrderApiService {
  private final RestTemplate orderRestTemplate;

  @Override
  public Optional<OrderDto> findById(UUID id) {
    var response = orderRestTemplate.getForEntity("lb://order-processing/orders/" + id, OrderDto.class);

    return switch (response.getStatusCode()) {
      case NOT_FOUND -> throw new DgsEntityNotFoundException();
      case UNAUTHORIZED -> throw new DgsBadRequestException();
      default -> Optional.ofNullable(response.getBody());
    };
  }

  @Override
  public List<OrderDto> userOrders() {
    var response = orderRestTemplate.getForEntity("lb://order-processing/orders/user", OrderDto[].class);
    return Arrays.asList(response.getBody());
  }

  @Override
  public List<OrderDto> allOrders() {
    var response = orderRestTemplate.getForEntity("lb://order-processing/orders", OrderDto[].class);
    return Arrays.asList(response.getBody());
  }

  @Override
  public Optional<OrderItemDto> findOrderItemById(UUID id) {
    // TODO impl
    return Optional.empty();
  }
}
