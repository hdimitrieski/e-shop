package com.eshop.catalog.application.commands.createproduct;

import com.eshop.catalog.application.commandbus.CatalogCommandHandler;
import com.eshop.catalog.application.models.CatalogItemResponse;
import com.eshop.catalog.domain.catalogitem.*;
import com.eshop.shared.rest.error.BadRequestException;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CreateProductCommandHandler implements CatalogCommandHandler<CatalogItemResponse, CreateProductCommand> {
  private final CatalogItemRepository catalogItemRepository;
  private final CategoryRepository categoryRepository;
  private final BrandRepository brandRepository;

  @Transactional
  @CommandHandler
  @Override
  public CatalogItemResponse handle(CreateProductCommand command) {

    final var catalogItemAggregate = catalogItemRepository.save(() -> catalogItemOf(command));

    return CatalogItemResponse.builder()
        .productId((UUID) catalogItemAggregate.identifier())
        .version(catalogItemAggregate.version())
        .build();
  }

  private CatalogItem catalogItemOf(CreateProductCommand command) {
    final var category = categoryRepository.findById(command.categoryId())
        .orElseThrow(() -> new BadRequestException("Category does not exist"));
    final var brand = brandRepository.findById(command.brandId())
        .orElseThrow(() -> new BadRequestException("Brand does not exist"));

    return new CatalogItem(
        ProductName.of(command.name()),
        command.description(),
        Price.of(command.price()),
        command.pictureFileName(),
        Units.of(command.availableStock()),
        category,
        brand
    );
  }

}
