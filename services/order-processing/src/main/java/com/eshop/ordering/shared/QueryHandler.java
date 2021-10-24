package com.eshop.ordering.shared;

import org.springframework.core.annotation.AliasFor;
import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * Annotation for the Query Handlers.
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface QueryHandler {

  /**
   * The value may indicate a suggestion for a logical component name,
   * to be turned into a Spring bean in case of an autodetected component.
   *
   * @return the suggested component name, if any or empty String otherwise
   */
  @AliasFor(annotation = Component.class)
  String value() default "";
}
