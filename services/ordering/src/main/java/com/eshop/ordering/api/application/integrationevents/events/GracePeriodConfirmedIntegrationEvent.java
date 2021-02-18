package com.eshop.ordering.api.application.integrationevents.events;

import com.eshop.ordering.api.application.integrationevents.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GracePeriodConfirmedIntegrationEvent extends IntegrationEvent {
    private Integer orderId;
}
