package com.eshop.basket.integrationevents.eventhandling;

import com.eshop.basket.integrationevents.events.ProductPriceChangedIntegrationEvent;
import com.eshop.basket.model.BasketRepository;
import com.eshop.basket.model.CustomerBasket;
import com.eshop.shared.eventhandling.IntegrationEventHandler;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class ProductPriceChangedIntegrationEventHandler implements IntegrationEventHandler<ProductPriceChangedIntegrationEvent> {
  private static final Logger logger = LoggerFactory.getLogger(ProductPriceChangedIntegrationEventHandler.class);

  private final BasketRepository basketRepository;

  @KafkaListener(
      groupId = "${app.kafka.group.productPriceChanges}",
      topics = "${spring.kafka.consumer.topic.productPriceChanges}"
  )
  @Override
  public void handle(ProductPriceChangedIntegrationEvent event) {
    logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());

    basketRepository.getUsers().forEach(userId -> basketRepository.findByCustomerId(userId)
        .ifPresent(basket -> updatePriceInBasketItems(
            event.getProductId(),
            event.getNewPrice(),
            event.getOldPrice(),
            basket
        ))
    );
  }

  private void updatePriceInBasketItems(UUID productId, Double newPrice, Double oldPrice, CustomerBasket basket) {
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
