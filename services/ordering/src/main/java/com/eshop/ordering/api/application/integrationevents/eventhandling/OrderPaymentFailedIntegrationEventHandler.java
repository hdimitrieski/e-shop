package com.eshop.ordering.api.application.integrationevents.eventhandling;

import com.eshop.ordering.api.application.integrationevents.events.OrderPaymentFailedIntegrationEvent;

public class OrderPaymentFailedIntegrationEventHandler {
    public void handle(OrderPaymentFailedIntegrationEvent event) {
//        using (LogContext.PushProperty("IntegrationEventContext", $"{@event.Id}-{Program.AppName}"))
//        {
//            _logger.LogInformation("----- Handling integration event: {IntegrationEventId} at {AppName} - ({@IntegrationEvent})", @event.Id, Program.AppName, @event);
//
//            var command = new CancelOrderCommand(@event.OrderId);
//
//            _logger.LogInformation(
//                    "----- Sending command: {CommandName} - {IdProperty}: {CommandId} ({@Command})",
//                    command.GetGenericTypeName(),
//                    nameof(command.OrderNumber),
//                    command.OrderNumber,
//                    command);
//
//            await _mediator.Send(command);
//        }
    }
}
