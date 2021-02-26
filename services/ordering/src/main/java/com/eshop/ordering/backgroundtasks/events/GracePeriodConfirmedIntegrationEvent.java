package com.eshop.ordering.backgroundtasks.events;

import com.eshop.ordering.api.application.integrationevents.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

// TODO HD move IntegrationEvent in shared project (BuildingBlocks)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GracePeriodConfirmedIntegrationEvent extends IntegrationEvent {
  private Integer orderId;
}
