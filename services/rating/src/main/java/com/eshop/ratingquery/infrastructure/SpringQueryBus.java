package com.eshop.ratingquery.infrastructure;


import com.eshop.ratingquery.application.querybus.Query;
import com.eshop.ratingquery.application.querybus.QueryBus;
import com.eshop.ratingquery.application.querybus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

//TODO IGI move this to a infrastructure module
@RequiredArgsConstructor
@Component
public class SpringQueryBus implements QueryBus {
  private final ObjectProvider<QueryHandler> queryHandlers;

  @Override
  public <R, Q extends Query<R>> R execute(Q query) {
    return (R) queryHandlerFor(query).handle(query);
  }

  public QueryHandler queryHandlerFor(Query query) {
    return queryHandlers.stream()
        .filter(queryHandler -> queryHandler.matches(query))
        .findFirst()
        .orElseThrow(() -> new IllegalArgumentException("Query handler not found"));
  }

}
