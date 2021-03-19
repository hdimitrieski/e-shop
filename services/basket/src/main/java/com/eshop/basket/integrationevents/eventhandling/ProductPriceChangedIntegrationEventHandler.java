package com.eshop.basket.integrationevents.eventhandling;

import com.eshop.basket.integrationevents.events.ProductPriceChangedIntegrationEvent;
import com.eshop.basket.model.BasketRepository;
import com.eshop.basket.model.CustomerBasket;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@RequiredArgsConstructor
@Component
public class ProductPriceChangedIntegrationEventHandler {
  private final BasketRepository basketRepository;

  @KafkaListener(groupId = "product-price-changes-group", topics = "${spring.kafka.consumer.topic.productPriceChanges}")
  public void handle(ProductPriceChangedIntegrationEvent event) {
    System.out.printf("----- Handling integration event: %s (%s)", event.getId(), event.getClass().getSimpleName());
    var userIds = basketRepository.getUsers();

    for (var id : userIds) {
      basketRepository.getBasket(id).ifPresent(basket ->
          updatePriceInBasketItems(event.getProductId(), event.getNewPrice(), event.getOldPrice(), basket)
      );
    }
  }

  private void updatePriceInBasketItems(Long productId, Double newPrice, Double oldPrice, CustomerBasket basket) {
    basket.getItems().stream().filter(x -> x.getProductId().equals(productId))
        .forEach(item -> {
          if (item.getUnitPrice().equals(oldPrice)) {
            var originalPrice = item.getUnitPrice();
            item.setUnitPrice(newPrice);
            item.setOldUnitPrice(originalPrice);
          }
        });
    basketRepository.updateBasket(basket);
  }
}
