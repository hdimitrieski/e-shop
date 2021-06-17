package com.eshop.graceperiodtask.events;

import com.eshop.shared.eventhandling.IntegrationEvent;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class GracePeriodConfirmedIntegrationEvent extends IntegrationEvent {
  private String orderId;
}
