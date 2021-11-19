package com.eshop.rating.application.integrationevents.eventhandling;

import com.eshop.rating.application.integrationevents.events.CatalogItemCreatedIntegrationEvent;
import com.eshop.rating.model.Rating;
import com.eshop.rating.model.RatingOption;
import com.eshop.rating.model.RatingRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.UUID;

@RequiredArgsConstructor
@Component
public class CatalogItemCreatedEventHandler {
    private static final Logger logger = LoggerFactory.getLogger(CatalogItemCreatedEventHandler.class);

    private final RatingRepository ratingRepository;

    @KafkaListener(groupId = "catalog-items-group", topics = "${spring.kafka.consumer.topic.catalogItemCreated}")
    public void handle(CatalogItemCreatedIntegrationEvent event) {
        logger.info("Handling integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
        var randomRating = RatingOption.values()[new Random().nextInt(RatingOption.values().length)];
        ratingRepository.save(new Rating(UUID.randomUUID(), event.getCatalogItemId(), randomRating));
    }
}
