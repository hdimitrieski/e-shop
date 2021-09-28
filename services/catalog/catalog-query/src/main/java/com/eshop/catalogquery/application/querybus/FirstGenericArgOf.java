package com.eshop.catalogquery.application.querybus;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class FirstGenericArgOf {
  private final Class<?> aClass;

  FirstGenericArgOf(Class<?> aClass) {
    this.aClass = aClass;
  }

  boolean isAssignableFrom(Class<?> otherClass) {
    Type[] interfaces = aClass.getGenericInterfaces();
    Type genericSuperclass = aClass.getGenericSuperclass();

    ParameterizedType type;
    if (interfaces.length > 0) {
      type = (ParameterizedType) interfaces[0];
    } else {
      type = (ParameterizedType) genericSuperclass;
    }

    Type handlerQuery = type.getActualTypeArguments()[1];
    Class<?> handlerQueryClass;

    if (handlerQuery instanceof ParameterizedType) {
      ParameterizedType parameterized = (ParameterizedType) handlerQuery;
      handlerQueryClass = (Class<?>) parameterized.getRawType();
    } else {
      handlerQueryClass = (Class<?>) handlerQuery;
    }

    return handlerQueryClass.isAssignableFrom(otherClass);
  }
}
