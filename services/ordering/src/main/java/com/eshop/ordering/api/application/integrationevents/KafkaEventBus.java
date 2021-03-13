package com.eshop.ordering.api.application.integrationevents;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaEventBus implements EventBus {

    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;
    private final String ordersTopic;

    public KafkaEventBus(
            KafkaTemplate<String, IntegrationEvent> kafkaTemplate,
            @Value("${spring.kafka.consumer.topic.order}") String ordersTopic
    ) {
        this.kafkaTemplate = kafkaTemplate;
        this.ordersTopic = ordersTopic;
    }

    @Override
    public void publish(IntegrationEvent event) {
        kafkaTemplate.send(ordersTopic, event);
    }
}
