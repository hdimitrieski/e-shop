package com.eshop.gqlgateway.config;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsRuntimeWiring;
import graphql.schema.idl.RuntimeWiring;
import graphql.validation.rules.OnValidationErrorStrategy;
import graphql.validation.rules.ValidationRules;
import graphql.validation.schemawiring.ValidationSchemaWiring;

@DgsComponent
public class AddExtendedValidationDirectiveWiring {

  @DgsRuntimeWiring
  public RuntimeWiring.Builder addGraphqlJavaExtendedValidationDirectives(RuntimeWiring.Builder builder) {
    // We register all the directives of graphql-java-extended-validation and let dgs know
    var validationRules = ValidationRules.newValidationRules()
      .onValidationErrorStrategy(OnValidationErrorStrategy.RETURN_NULL)
      .build();

    // This will rewrite your data fetchers when rules apply to them so that they get validated
    var schemaWiring = new ValidationSchemaWiring(validationRules);
    return builder.directiveWiring(schemaWiring);
  }

}
