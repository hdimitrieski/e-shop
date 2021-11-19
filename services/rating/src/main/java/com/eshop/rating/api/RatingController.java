package com.eshop.rating.api;


import com.eshop.rating.application.commandbus.RatingCommandBus;
import com.eshop.rating.application.commands.AddRatingCommand;
import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.application.queries.ratingforcatalogitem.RatingForCatalogItemQuery;
import com.eshop.rating.application.querybus.QueryBus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RequestMapping("rating")
@RestController
@RequiredArgsConstructor
public class RatingController {
  private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

  private final QueryBus queryBus;
  private final RatingCommandBus commandBus;

  @RequestMapping("/{catalogItemId}")
  public ResponseEntity<RatingForCatalogItemDto> ratingsByCatalogItemId(@PathVariable UUID catalogItemId) {
    logger.info("Getting ratings by catalog item with id: {}", catalogItemId);
    var response = queryBus.execute(new RatingForCatalogItemQuery(catalogItemId));
    return ResponseEntity.ok(response);
  }

  @PostMapping("/{catalogItemId}")
  public ResponseEntity<RatingForCatalogItemDto> addRating(@PathVariable UUID catalogItemId, @RequestBody @Valid AddRatingCommand command) {
    logger.info("Adding rating: {} to catalog item with id: {}", command.rating(), catalogItemId);
    var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

}
