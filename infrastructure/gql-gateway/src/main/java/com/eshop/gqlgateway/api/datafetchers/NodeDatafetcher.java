package com.eshop.gqlgateway.api.datafetchers;

import com.eshop.gqlgateway.types.Node;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import graphql.execution.DataFetcherResult;
import lombok.RequiredArgsConstructor;

import static com.eshop.gqlgateway.api.util.IdUtils.fromString;

@RequiredArgsConstructor
@DgsComponent
public class NodeDatafetcher {
  private final NodeResolver nodeResolver;

  @DgsQuery
  public DataFetcherResult<? extends Node> node(@InputArgument String id) {
    return nodeResolver.resolve(fromString(id));
  }
}
