package com.eshop.catalog.api;

import com.eshop.catalog.application.commandbus.CatalogCommandBus;
import com.eshop.catalog.application.commands.addstock.AddStockCommand;
import com.eshop.catalog.application.commands.changeprice.ChangePriceCommand;
import com.eshop.catalog.application.commands.changeproductname.ChangeProductNameCommand;
import com.eshop.catalog.application.commands.createproduct.CreateProductCommand;
import com.eshop.catalog.application.commands.removestock.RemoveStockCommand;
import com.eshop.catalog.application.models.CatalogItemResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * Handle requests for catalog service.
 */
@RequestMapping("catalog")
@RestController
@RequiredArgsConstructor
public class CatalogController {
  private static final Logger logger = LoggerFactory.getLogger(CatalogController.class);

  private final CatalogCommandBus commandBus;

  @RequestMapping(method = RequestMethod.POST, path = "items")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<CatalogItemResponse> createProduct(@RequestBody @Valid CreateProductCommand command) {
    logger.info("Create product requested");
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/{id}/changeprice")
  public ResponseEntity<CatalogItemResponse> changeProductPrice(@RequestBody @Valid ChangePriceCommand command) {
    logger.info("Change price request for product: {}", command.productId());
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/{id}/addstock")
  public ResponseEntity<CatalogItemResponse> addStock(@RequestBody @Valid AddStockCommand command) {
    logger.info("Add stock request for product: {}", command.productId());
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/{id}/removestock")
  public ResponseEntity<CatalogItemResponse> removeStock(@RequestBody @Valid RemoveStockCommand command) {
    logger.info("Remove stock request for product: {}", command.productId());
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  @RequestMapping(method = RequestMethod.PUT, path = "items/{id}/changeproductname")
  public ResponseEntity<CatalogItemResponse> changeProductName(@RequestBody @Valid ChangeProductNameCommand command) {
    logger.info("Change product name request for product: {}", command.productId());
    final var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

  private ResponseEntity<CatalogItemResponse> response(CatalogItemResponse catalogItemResponse) {
    return ResponseEntity
        .ok()
        .headers(httpHeaders -> httpHeaders.add("Retry-after", "2"))
        .eTag(catalogItemResponse.getVersion().toString())
        .body(catalogItemResponse);
  }

}
