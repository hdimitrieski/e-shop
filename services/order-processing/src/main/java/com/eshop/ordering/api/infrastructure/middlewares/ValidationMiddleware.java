package com.eshop.ordering.api.infrastructure.middlewares;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolationException;
import javax.validation.Validation;

@Component
@AllArgsConstructor
@Order(1)
public class ValidationMiddleware implements Command.Middleware {
  private static final Logger logger = LoggerFactory.getLogger(ValidationMiddleware.class);

  @Override
  public <R, C extends Command<R>> R invoke(C command, Next<R> next) {
    logger.info("Validating command {}", command.getClass().getSimpleName());

    var factory = Validation.buildDefaultValidatorFactory();
    var validator = factory.getValidator();
    var violations = validator.validate(command);

    if (!violations.isEmpty()) {
      throw new ConstraintViolationException(violations);
    }

    return next.invoke();
  }

}
