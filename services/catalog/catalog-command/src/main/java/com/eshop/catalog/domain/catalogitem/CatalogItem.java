package com.eshop.catalog.domain.catalogitem;

import com.eshop.catalog.domain.base.AggregateRoot;
import com.eshop.catalog.domain.catalogitem.rules.AvailableStockMustNotBeEmpty;
import com.eshop.catalog.domain.catalogitem.rules.PriceMustBeGreaterThanZero;
import com.eshop.catalog.domain.catalogitem.rules.QuantityMustBeGreaterThanZero;
import com.eshop.catalog.shared.events.*;
import com.eshop.catalog.shared.model.BrandDto;
import com.eshop.catalog.shared.model.CategoryDto;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.axonframework.eventsourcing.EventSourcingHandler;
import org.axonframework.spring.stereotype.Aggregate;
import org.springframework.lang.NonNull;

import java.util.UUID;

import static java.util.Objects.requireNonNull;
import static org.axonframework.modelling.command.AggregateLifecycle.apply;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Aggregate
public class CatalogItem extends AggregateRoot {
  private ProductName name;
  private String description;
  @Getter
  private Price price;
  private String pictureFileName;
  @Getter
  private Units availableStock;
  private Category category;
  private Brand brand;

  public CatalogItem(
      @NonNull ProductName productName,
      String description,
      @NonNull Price price,
      String pictureFileName,
      @NonNull Units availableStock,
      @NonNull Category category,
      @NonNull Brand brand
  ) {
    super(UUID.randomUUID());
    setName(productName);
    setDescription(description);
    setPrice(price);
    setPictureFileName(pictureFileName);
    setAvailableStock(availableStock);
    setCategory(category);
    setBrand(brand);

    apply(new
        CatalogItemCreated(
        id,
        name.getName(),
        description,
        price.getValue(),
        pictureFileName,
        availableStock.getValue(),
        new CategoryDto(category.getCategoryId(), category.getName()),
        new BrandDto(brand.getBrandId(), brand.getName())
    ));
  }

  /**
   * If there is sufficient stock of an item, then the integer returned at the end of this call should be the same as
   * quantityDesired.
   * In the event that there isn't sufficient stock available, the method will remove whatever stock is available
   * and return that quantity to the client.
   * In this case, it is the responsibility of the client to determine if the amount that is returned is the same as
   * quantityDesired.
   */
  public Units removeStock(Units quantityDesired) {
    checkRule(new AvailableStockMustNotBeEmpty(name, availableStock));
    checkRule(new QuantityMustBeGreaterThanZero(quantityDesired));

    final var updatedStock = quantityDesired.greaterThan(availableStock)
        ? Units.empty()
        : availableStock.subtract(quantityDesired);

    apply(new StockRemoved(id, updatedStock.getValue()));

    return updatedStock;
  }

  /**
   * Increments the quantity of a particular item in inventory.
   *
   * @return the quantity that has been added to stock
   */
  public Units addStock(Units quantity) {
    checkRule(new QuantityMustBeGreaterThanZero(quantity));
    final var originalStock = availableStock;
    final var updatedStock = availableStock.add(quantity);

    apply(new StockAdded(id, updatedStock.getValue()));

    return availableStock.subtract(originalStock);
  }

  public void changePrice(@NonNull Price newPrice) {
    requireNonNull(price, "Price cannot be null");
    checkRule(new PriceMustBeGreaterThanZero(newPrice));

    apply(new ProductPriceChanged(id, newPrice.getValue()));
  }

  public void changeName(@NonNull ProductName name) {
    requireNonNull(price, "Product name cannot be null");

    apply(new ProductNameChanged(id, name.getName()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(CatalogItemCreated event) {
    this.id = event.getId();
    setName(ProductName.of(event.getName()));
    setDescription(event.getDescription());
    setPrice(Price.of(event.getPrice()));
    setPictureFileName(event.getPictureFileName());
    setAvailableStock(Units.of(event.getAvailableStock()));
    setCategory(Category.of(event.getCategory().getId(), event.getCategory().getName()));
    setBrand(Brand.of(event.getBrand().getId(), event.getBrand().getName()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(StockAdded event) {
    setAvailableStock(Units.of(event.getAvailableStock()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(StockRemoved event) {
    setAvailableStock(Units.of(event.getAvailableStock()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(ProductNameChanged event) {
    setName(ProductName.of(event.getName()));
  }

  @SuppressWarnings("unused")
  @EventSourcingHandler
  private void on(ProductPriceChanged event) {
    setPrice(Price.of(event.getPrice()));
  }

  private void setName(ProductName name) {
    requireNonNull(name, "Product name cannot be null");
    this.name = name;
  }

  private void setDescription(String description) {
    this.description = description;
  }

  private void setPrice(Price price) {
    this.price = price;
  }

  private void setPictureFileName(String pictureFileName) {
    this.pictureFileName = pictureFileName;
  }

  private void setAvailableStock(Units availableStock) {
    requireNonNull(availableStock, "Available stock cannot be null");
    this.availableStock = availableStock;
  }

  private void setCategory(Category category) {
    requireNonNull(category, "Category cannot be null");
    this.category = category;
  }

  private void setBrand(Brand brand) {
    requireNonNull(brand, "Brand cannot be null");
    this.brand = brand;
  }

}
