package com.eshop.gateway.infrastructure;

import com.eshop.gateway.infrastructure.exception.ServiceCallFailedException;
import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.models.OrderData;
import com.eshop.gateway.models.OrderItemData;
import com.eshop.gateway.services.OrderingApiService;
import com.eshop.gateway.services.dtos.BasketItem;
import com.eshop.gateway.services.dtos.CreateOrderDraftRequest;
import com.eshop.gateway.services.dtos.OrderDraftDTO;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

@RequiredArgsConstructor
@Service
public class OrderingApiServiceImpl implements OrderingApiService {
  private final WebClient.Builder orderWebClient;

  @CircuitBreaker(name = "order")
  @Override
  public Mono<OrderData> getOrderDraft(BasketData basket) {
    var request = new CreateOrderDraftRequest(
        basket.buyerId(),
        basket.items().stream().map(item -> new BasketItem(
            item.id(),
            item.productId(),
            item.productName(),
            item.unitPrice(),
            item.oldUnitPrice(),
            item.quantity(),
            item.pictureUrl()
        )).collect(Collectors.toList())
    );

    return orderWebClient.build()
        .post()
        .uri("lb://order-processing/orders/draft")
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
        .onStatus(HttpStatus::is5xxServerError, clientResponse -> Mono.error(new ServiceCallFailedException()))
        .bodyToMono(OrderDraftDTO.class)
        .map(orderDraft -> new OrderData(
            basket.buyerId(),
            orderDraft.total(),
            orderDraft.orderItems().stream().map(item -> new OrderItemData(
                item.productId(),
                item.productName(),
                item.unitPrice(),
                item.discount(),
                item.units(),
                item.pictureUrl()
            )).collect(Collectors.toList())
        ));
  }
}
