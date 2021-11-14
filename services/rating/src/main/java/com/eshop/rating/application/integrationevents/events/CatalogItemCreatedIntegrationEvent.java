package com.eshop.rating.application.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class CatalogItemCreatedIntegrationEvent extends IntegrationEvent {
    private UUID catalogItemId;
}
