package com.eshop.ordering.api.application.integrationevents.eventhandling;

import com.eshop.ordering.api.application.integrationevents.events.GracePeriodConfirmedIntegrationEvent;

public class GracePeriodConfirmedIntegrationEventHandler {

    /// <summary>
    /// Event handler which confirms that the grace period
    /// has been completed and order will not initially be cancelled.
    /// Therefore, the order process continues for validation.
    /// </summary>
    /// <param name="event">
    /// </param>
    /// <returns></returns>
    public void handle(GracePeriodConfirmedIntegrationEvent event) {
//        _logger.LogInformation("----- Handling integration event: {IntegrationEventId} at {AppName} - ({@IntegrationEvent})", @event.Id, Program.AppName, @event);
//
//        var command = new SetAwaitingValidationOrderStatusCommand(@event.OrderId);
//
//        _logger.LogInformation(
//                "----- Sending command: {CommandName} - {IdProperty}: {CommandId} ({@Command})",
//                command.GetGenericTypeName(),
//                nameof(command.OrderNumber),
//                command.OrderNumber,
//                command);
//
//        await _mediator.Send(command);
    }
}
