package com.eshop.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDate;

import static java.util.Objects.nonNull;

public class CardExpirationDateValidator implements ConstraintValidator<CardExpirationDate, LocalDate> {

  @Override
  public boolean isValid(LocalDate expiration, ConstraintValidatorContext constraintValidatorContext) {
    return nonNull(expiration) && expiration.isAfter(LocalDate.now());
  }
}
