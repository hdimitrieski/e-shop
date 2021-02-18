package com.eshop.ordering.api.application.commands;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class SetPaidOrderStatusCommand {
    private final Integer orderNumber;
}
