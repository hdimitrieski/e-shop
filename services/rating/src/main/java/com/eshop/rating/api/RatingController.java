package com.eshop.rating.api;


import com.eshop.rating.application.commands.AddRatingCommand;
import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.application.queries.ratingforcatalogitem.RatingByCatalogItemIdsQuery;
import com.eshop.rating.application.queries.ratingforcatalogitem.RatingForCatalogItemQuery;
import com.eshop.rating.application.shared.CommandBus;
import com.eshop.rating.application.shared.QueryBus;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("rating")
@RestController
@RequiredArgsConstructor
public class RatingController {
  private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

  private final QueryBus queryBus;
  private final CommandBus commandBus;

  @RequestMapping("/{catalogItemId}")
  public ResponseEntity<RatingForCatalogItemDto> ratingsByCatalogItemId(@PathVariable UUID catalogItemId) {
    logger.info("Getting ratings by catalog item with id: {}", catalogItemId);
    var response = queryBus.execute(new RatingForCatalogItemQuery(catalogItemId));
    return ResponseEntity.ok(response);
  }

  @RequestMapping("/withids/{ids}")
  public Iterable<RatingForCatalogItemDto> ratingsByCatalogItemIds(@PathVariable String ids) {
    logger.info("Getting ratings by catalog item with ids");
    final var catalogItemIds = Arrays.
      stream(ids.split(","))
      .map(UUID::fromString)
      .collect(Collectors.toSet());
    return queryBus.execute(new RatingByCatalogItemIdsQuery(catalogItemIds));
  }

  @PostMapping("/{catalogItemId}")
  public ResponseEntity<RatingForCatalogItemDto> addRating(
    @PathVariable UUID catalogItemId,
    @RequestBody @Valid AddRatingCommand command
  ) {
    logger.info("Adding rating: {} to catalog item with id: {}", command.rating(), catalogItemId);
    var response = commandBus.execute(command);
    return ResponseEntity.ok(response);
  }

}
