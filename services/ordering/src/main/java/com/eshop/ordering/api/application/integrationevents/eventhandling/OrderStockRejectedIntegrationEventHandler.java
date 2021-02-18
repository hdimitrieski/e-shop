package com.eshop.ordering.api.application.integrationevents.eventhandling;

import com.eshop.ordering.api.application.integrationevents.events.OrderStockRejectedIntegrationEvent;

public class OrderStockRejectedIntegrationEventHandler {
    public void handle(OrderStockRejectedIntegrationEvent event) {
//        using (LogContext.PushProperty("IntegrationEventContext", $"{@event.Id}-{Program.AppName}"))
//        {
//            _logger.LogInformation("----- Handling integration event: {IntegrationEventId} at {AppName} - ({@IntegrationEvent})", @event.Id, Program.AppName, @event);
//
//            var orderStockRejectedItems = @event.OrderStockItems
//                .FindAll(c => !c.HasStock)
//                    .Select(c => c.ProductId)
//                    .ToList();
//
//            var command = new SetStockRejectedOrderStatusCommand(@event.OrderId, orderStockRejectedItems);
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
