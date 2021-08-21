package com.eshop.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CardExpirationDateValidator.class)
public @interface CardExpirationDate {
  String message() default "Invalid expiration date";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
