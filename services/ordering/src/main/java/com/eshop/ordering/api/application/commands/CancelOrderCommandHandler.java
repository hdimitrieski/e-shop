package com.eshop.ordering.api.application.commands;

import an.awesome.pipelinr.Command;
import com.eshop.ordering.domain.aggregatesmodel.order.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CancelOrderCommandHandler implements Command.Handler<CancelOrderCommand, Boolean> {
    private final OrderRepository orderRepository;

    /**
     * Handler which processes the command when customer executes cancel order from app.
     */
    @Override
    public Boolean handle(CancelOrderCommand command) {
        var orderToUpdate = orderRepository.get(command.getOrderNumber());
        if (orderToUpdate == null) {
            return false;
        }
        orderToUpdate.setCancelledStatus();
        return true;
//        return orderRepository.unitOfWork().saveEntities();
    }
}
