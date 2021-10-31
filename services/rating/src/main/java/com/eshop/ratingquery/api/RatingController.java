package com.eshop.ratingquery.api;


import com.eshop.ratingquery.application.queries.ratingsbycatalogitem.RatingsByCatalogItemIdQuery;
import com.eshop.ratingquery.application.querybus.QueryBus;
import com.eshop.ratingquery.model.Rating;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RequestMapping("rating")
@RestController
@RequiredArgsConstructor
public class RatingController {
  private static final Logger logger = LoggerFactory.getLogger(RatingController.class);

  private final QueryBus queryBus;

  @RequestMapping("/{catalogItemId}")
  public Iterable<Rating> ratingsByCatalogItemId(@PathVariable UUID catalogItemId) {
    logger.info("Getting ratings by catalog item with id: {}", catalogItemId);

    return queryBus.execute(new RatingsByCatalogItemIdQuery(catalogItemId));
  }

}
