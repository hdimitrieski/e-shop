package com.eshop.catalog.application.integrationevents.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import java.util.UUID;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class CatalogItemCreatedIntegrationEvent extends IntegrationEvent {
    private UUID catalogItemId;
}
