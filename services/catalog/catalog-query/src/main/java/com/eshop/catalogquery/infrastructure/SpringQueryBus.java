package com.eshop.catalogquery.infrastructure;

import com.eshop.catalogquery.application.querybus.Query;
import com.eshop.catalogquery.application.querybus.QueryBus;
import com.eshop.catalogquery.application.querybus.QueryHandler;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.stereotype.Component;

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
