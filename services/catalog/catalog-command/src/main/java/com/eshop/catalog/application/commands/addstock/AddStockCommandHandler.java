package com.eshop.catalog.application.commands.addstock;

import com.eshop.catalog.application.commandbus.CatalogCommandHandler;
import com.eshop.catalog.application.models.CatalogItemResponse;
import com.eshop.catalog.domain.catalogitem.CatalogItemRepository;
import com.eshop.catalog.domain.catalogitem.Units;
import com.eshop.shared.rest.error.NotFoundException;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import static java.util.Objects.isNull;

@RequiredArgsConstructor
@Component
public class AddStockCommandHandler implements CatalogCommandHandler<CatalogItemResponse, AddStockCommand> {
  private final CatalogItemRepository catalogItemRepository;

  @CommandHandler
  @Override
  public CatalogItemResponse handle(AddStockCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());

    if (isNull(catalogItem)) {
      throw new NotFoundException("Catalog item not found");
    }

    catalogItem.execute(c -> c.addStock(Units.of(command.quantity())));

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }
}
