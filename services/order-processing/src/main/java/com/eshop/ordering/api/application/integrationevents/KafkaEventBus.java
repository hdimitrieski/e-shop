package com.eshop.ordering.api.application.integrationevents;

import com.eshop.eventbus.IntegrationEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventBus implements EventBus {
    private static final Logger logger = LoggerFactory.getLogger(KafkaEventBus.class);
    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

    public KafkaEventBus(
            KafkaTemplate<String, IntegrationEvent> kafkaTemplate
    ) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void publish(String topic, IntegrationEvent event) {
        logger.info("Publishing integration event: {} ({}) to topic: {}", event.getId(), event.getClass().getSimpleName(), topic);
        kafkaTemplate.send(topic, event);
    }
}
