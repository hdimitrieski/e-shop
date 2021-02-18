package com.eshop.ordering.api.application.integrationevents.eventhandling;

import com.eshop.ordering.api.application.integrationevents.events.UserCheckoutAcceptedIntegrationEvent;

public class UserCheckoutAcceptedIntegrationEventHandler {

    /// <summary>
    /// Integration event handler which starts the create order process
    /// </summary>
    /// <param name="@event">
    /// Integration event message which is sent by the
    /// basket.api once it has successfully process the
    /// order items.
    /// </param>
    /// <returns></returns>
    public void handle(UserCheckoutAcceptedIntegrationEvent event) {
//        using (LogContext.PushProperty("IntegrationEventContext", $"{@event.Id}-{Program.AppName}"))
//        {
//            _logger.LogInformation("----- Handling integration event: {IntegrationEventId} at {AppName} - ({@IntegrationEvent})", @event.Id, Program.AppName, @event);
//
//            var result = false;
//
//            if (@event.RequestId != Guid.Empty)
//            {
//                using (LogContext.PushProperty("IdentifiedCommandId", @event.RequestId))
//                {
//                    var createOrderCommand = new CreateOrderCommand(@event.Basket.Items, @event.UserId, @event.UserName, @event.City, @event.Street,
//                    @event.State, @event.Country, @event.ZipCode,
//                    @event.CardNumber, @event.CardHolderName, @event.CardExpiration,
//                    @event.CardSecurityNumber, @event.CardTypeId);
//
//                    var requestCreateOrder = new IdentifiedCommand<CreateOrderCommand, bool>(createOrderCommand, @event.RequestId);
//
//                    _logger.LogInformation(
//                            "----- Sending command: {CommandName} - {IdProperty}: {CommandId} ({@Command})",
//                            requestCreateOrder.GetGenericTypeName(),
//                            nameof(requestCreateOrder.Id),
//                            requestCreateOrder.Id,
//                            requestCreateOrder);
//
//                    result = await _mediator.Send(requestCreateOrder);
//
//                    if (result)
//                    {
//                        _logger.LogInformation("----- CreateOrderCommand suceeded - RequestId: {RequestId}", @event.RequestId);
//                    }
//                    else
//                    {
//                        _logger.LogWarning("CreateOrderCommand failed - RequestId: {RequestId}", @event.RequestId);
//                    }
//                }
//            }
//                else
//            {
//                _logger.LogWarning("Invalid IntegrationEvent - RequestId is missing - {@IntegrationEvent}", @event);
//            }
//        }
    }
}
