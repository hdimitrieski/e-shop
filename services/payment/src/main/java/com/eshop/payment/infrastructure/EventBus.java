package com.eshop.payment.infrastructure;

import com.eshop.payment.events.IntegrationEvent;

// TODO HD move in shared project (BuildingBlocks)
public interface EventBus {
    void publish(IntegrationEvent event);
}
