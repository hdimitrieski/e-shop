package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import com.eshop.ordering.infrastructure.idempotency.RequestManager;
import lombok.RequiredArgsConstructor;

/**
 * Provides a base implementation for handling duplicate request and ensuring idempotent updates, in the cases where
 * a requestid sent by client is used to detect duplicate requests.
 * <p>
 * T: Type of the command handler that performs the operation if request is not duplicated
 * R: Return value of the inner command handler
 */
@RequiredArgsConstructor
public abstract class IdentifiedCommandHandler<T extends Command<R>, R>
    implements Command.Handler<IdentifiedCommand<T, R>, R> {

  private final Pipeline pipeline;
  private final RequestManager requestManager;

  /**
   * Creates the result value to return if a previous request was found.
   */
  protected abstract R createResultForDuplicateRequest();

  @Override
  public R handle(IdentifiedCommand<T, R> identifiedCommand) {

    if (requestManager.exist(identifiedCommand.id())) {
      return createResultForDuplicateRequest();
    }
    requestManager.createRequestForCommand(identifiedCommand.id(), identifiedCommand.getClass().getSimpleName());

    var command = identifiedCommand.command();
    var idProperty = "";
    var commandId = "";

    if (command instanceof CreateOrderCommand createOrderCommand) {
      idProperty = "userId";
      commandId = createOrderCommand.getUserId();
    } else if (command instanceof CancelOrderCommand cancelOrderCommand) {
      idProperty = "orderNumber";
      commandId = cancelOrderCommand.orderNumber().toString();
    } else if (command instanceof ShipOrderCommand shipOrderCommand) {
      idProperty = "orderNumber";
      commandId = shipOrderCommand.orderNumber().toString();
    } else {
      idProperty = "Id?";
      commandId = "n/a";
    }

    System.out.printf(
        "----- Sending command: {%s} - {%s}: {%s}",
        command.getClass().getSimpleName(),
        idProperty,
        commandId);

    // Send the embedded business command to pipelinr so it runs its related CommandHandler
    var result = command.execute(pipeline);

    System.out.printf(
        "----- Command result: {%s} - {%s} - {%s}: {%s}",
        result,
        command.getClass().getSimpleName(),
        idProperty,
        commandId);

    return result;
  }
}
