package com.eshop.ordering.api.application.integrationevents;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.UUID;

/**
 * An Integration Event is an event that can cause side effects to other microservices, Bounded-Contexts or external systems.
 */
// TODO HD move to shared maven module
@Data
@AllArgsConstructor
public class IntegrationEvent {
    private UUID id;
    private LocalDateTime creationDate;

    public IntegrationEvent() {
        this(UUID.randomUUID(), LocalDateTime.now());
    }
}
