package com.eshop.rating.application.shared;

public interface QueryBus {
  <R, Q extends Query<R>> R execute(Q query);
}
