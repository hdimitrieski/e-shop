package com.eshop.gqlgateway.infrastructure;

import com.eshop.gqlgateway.models.OrderDto;
import com.eshop.gqlgateway.models.OrderItemDto;
import com.eshop.gqlgateway.services.OrderApiService;
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
    return Optional.ofNullable(orderRestTemplate.getForObject("lb://order-processing/orders/" + id, OrderDto.class));
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
