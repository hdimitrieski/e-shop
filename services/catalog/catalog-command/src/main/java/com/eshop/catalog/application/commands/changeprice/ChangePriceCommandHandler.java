package com.eshop.catalog.application.commands.changeprice;

import com.eshop.catalog.application.commandbus.CatalogCommandHandler;
import com.eshop.catalog.application.integrationevents.IntegrationEventPublisher;
import com.eshop.catalog.application.integrationevents.events.ProductPriceChangedIntegrationEvent;
import com.eshop.catalog.application.models.CatalogItemResponse;
import com.eshop.catalog.config.KafkaTopics;
import com.eshop.catalog.domain.catalogitem.CatalogItemRepository;
import com.eshop.catalog.domain.catalogitem.Price;
import com.eshop.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.Function;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class ChangePriceCommandHandler implements CatalogCommandHandler<CatalogItemResponse, ChangePriceCommand> {
  private final IntegrationEventPublisher integrationEventPublisher;
  private final CatalogItemRepository catalogItemRepository;
  private final KafkaTopics topics;

  @CommandHandler
  @Transactional
  @Override
  public CatalogItemResponse handle(ChangePriceCommand command) {
    final var catalogItemAggregate = catalogItemRepository.loadAggregate(command.productId());

    if (isNull(catalogItemAggregate)) {
      throw new NotFoundException("Catalog item not found");
    }

    final var catalogItem = catalogItemAggregate.invoke(Function.identity());
    final var price = Price.of(command.price());
    var priceChangedEvent = new ProductPriceChangedIntegrationEvent(
        catalogItem.getId(),
        price.getValue(),
        catalogItem.getPrice().getValue()
    );

    catalogItemAggregate.execute(c -> c.changePrice(price));

    // TODO publish PriceChangedEvent in a domain event handler
    integrationEventPublisher.publish(topics.getProductPriceChanges(), priceChangedEvent);

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItemAggregate.version())
        .build();
  }
}
