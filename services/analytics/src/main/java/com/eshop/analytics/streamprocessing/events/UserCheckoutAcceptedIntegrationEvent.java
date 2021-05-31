package com.eshop.analytics.streamprocessing.events;

import com.eshop.analytics.model.CustomerBasket;
import com.eshop.shared.eventhandling.IntegrationEvent;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@JsonIgnoreProperties(ignoreUnknown = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class UserCheckoutAcceptedIntegrationEvent extends IntegrationEvent {
  private String userId;
  private CustomerBasket basket;
}
