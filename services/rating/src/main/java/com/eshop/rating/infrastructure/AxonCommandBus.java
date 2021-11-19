package com.eshop.rating.infrastructure;

import com.eshop.rating.application.commandbus.Command;
import com.eshop.rating.application.commandbus.RatingCommandBus;
import lombok.RequiredArgsConstructor;
import org.axonframework.commandhandling.gateway.CommandGateway;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class AxonCommandBus implements RatingCommandBus {
    private final CommandGateway commandGateway;

    @Override
    public <R, C extends Command<R>> R execute(C command) {
        return commandGateway.sendAndWait(command);
    }
}
