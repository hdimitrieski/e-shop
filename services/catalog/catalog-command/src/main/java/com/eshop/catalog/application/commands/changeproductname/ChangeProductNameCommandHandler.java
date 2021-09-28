package com.eshop.catalog.application.commands.changeproductname;

import com.eshop.catalog.application.commandbus.CatalogCommandHandler;
import com.eshop.catalog.application.models.CatalogItemResponse;
import com.eshop.catalog.domain.catalogitem.CatalogItemRepository;
import com.eshop.catalog.domain.catalogitem.ProductName;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ChangeProductNameCommandHandler implements CatalogCommandHandler<CatalogItemResponse, ChangeProductNameCommand> {
  private final CatalogItemRepository catalogItemRepository;

  @CommandHandler
  public CatalogItemResponse handle(ChangeProductNameCommand command) {
    final var catalogItem = catalogItemRepository.loadAggregate(command.productId());

    catalogItem.execute(c -> c.changeName(ProductName.of(command.name())));

    return CatalogItemResponse.builder()
        .productId(command.productId())
        .version(catalogItem.version())
        .build();
  }
}
