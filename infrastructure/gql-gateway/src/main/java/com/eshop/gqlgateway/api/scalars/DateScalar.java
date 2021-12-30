package com.eshop.gqlgateway.api.scalars;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.scalars.ExtendedScalars;
import graphql.schema.idl.RuntimeWiring;

@DgsComponent
public class DateScalar {

  @DgsRuntimeWiring
  public RuntimeWiring.Builder addScalar(RuntimeWiring.Builder builder) {
    return builder.scalar(ExtendedScalars.Date);
  }
}
