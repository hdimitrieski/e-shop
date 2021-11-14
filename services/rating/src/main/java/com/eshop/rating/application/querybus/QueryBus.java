package com.eshop.rating.application.querybus;

public interface QueryBus {
  <R, Q extends Query<R>> R execute(Q query);
}
