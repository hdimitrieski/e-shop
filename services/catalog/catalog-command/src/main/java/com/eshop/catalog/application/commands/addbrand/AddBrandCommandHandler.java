package com.eshop.catalog.application.commands.addbrand;

import com.eshop.catalog.application.commandbus.CatalogCommandHandler;
import com.eshop.catalog.domain.catalogitem.Brand;
import com.eshop.catalog.domain.catalogitem.BrandRepository;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.CommandHandler;
import org.springframework.stereotype.Component;

import java.util.UUID;

@RequiredArgsConstructor
@Component
public class AddBrandCommandHandler implements CatalogCommandHandler<AddBrandResponse, AddBrandCommand> {
  private final BrandRepository brandRepository;

  @CommandHandler
  @Override
  public AddBrandResponse handle(AddBrandCommand command) {
    final var brand = brandRepository.save(Brand.of(UUID.randomUUID(), command.name()));

    return AddBrandResponse.builder()
        .brandName(brand.getName())
        .build();
  }
}
