package com.eshop.gateway.infrastructure;

import com.eshop.gateway.models.BasketData;
import com.eshop.gateway.models.OrderData;
import com.eshop.gateway.models.OrderItemData;
import com.eshop.gateway.services.OrderingApiService;
import com.eshop.gateway.services.dtos.BasketItem;
import com.eshop.gateway.services.dtos.CreateOrderDraftRequest;
import com.eshop.gateway.services.dtos.OrderDraftDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.stream.Collectors;

import static org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction.clientRegistrationId;

@RequiredArgsConstructor
@Service
public class OrderingApiServiceImpl implements OrderingApiService {
  private final WebClient webClient;
  private static final String clientId = "gateway";

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

    return webClient
        .post()
        .uri("http://localhost:8082/orders/draft")
        .attributes(clientRegistrationId(clientId))
        .contentType(MediaType.APPLICATION_JSON)
        .bodyValue(request)
        .retrieve()
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
