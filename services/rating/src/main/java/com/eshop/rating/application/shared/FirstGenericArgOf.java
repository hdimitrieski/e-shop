package com.eshop.rating.application.shared;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

final class FirstGenericArgOf {
  private final Class<?> aClass;

  FirstGenericArgOf(Class<?> aClass) {
    this.aClass = aClass;
  }

  boolean isAssignableFrom(Class<?> otherClass) {
    Type[] interfaces = aClass.getGenericInterfaces();
    Type genericSuperclass = aClass.getGenericSuperclass();

    ParameterizedType type = (ParameterizedType) (interfaces.length > 0 ? interfaces[0] : genericSuperclass);

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
