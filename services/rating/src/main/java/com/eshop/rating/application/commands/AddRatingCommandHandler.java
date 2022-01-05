package com.eshop.rating.application.commands;

import com.eshop.rating.application.shared.CommandHandler;
import com.eshop.rating.application.model.RatingForCatalogItemDto;
import com.eshop.rating.application.services.CalculateRatingForCatalogItemService;
import com.eshop.rating.model.Rating;
import com.eshop.rating.model.RatingRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@RequiredArgsConstructor
@Component
@Slf4j
public class AddRatingCommandHandler implements CommandHandler<RatingForCatalogItemDto, AddRatingCommand> {

  private final CalculateRatingForCatalogItemService calculateRatingForCatalogItemService;
  private final RatingRepository ratingRepository;

  @Override
  @Transactional
  public RatingForCatalogItemDto handle(AddRatingCommand command) {
    var newRating = Rating.builder()
      .id(UUID.randomUUID())
      .rating(command.rating())
      .catalogItemId(command.catalogItemId())
      .build();
    log.info("Creating new rating: {} for catalog item id: {}", command.rating(), command.catalogItemId());
    ratingRepository.save(newRating);
    return calculateRatingForCatalogItemService.calculate(command.catalogItemId());
  }
}
