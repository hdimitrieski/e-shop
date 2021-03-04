package com.eshop.ordering.api.application.behaviours;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

@Component
@AllArgsConstructor
@Order(1)
public class ValidatorBehaviour implements Command.Middleware {

  @Override
  public <R, C extends Command<R>> R invoke(C command, Next<R> next) {
    System.out.printf("----- Validating command {%s}\n", command.getClass().getSimpleName());

    var factory = Validation.buildDefaultValidatorFactory();
    var validator = factory.getValidator();
    var violations = validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    return next.invoke();
  }

}
