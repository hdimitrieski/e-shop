package com.eshop.gqlgateway.api.context;

import com.eshop.gqlgateway.services.UserService;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.context.DgsCustomContextBuilderWithRequest;
import lombok.RequiredArgsConstructor;
import org.jetbrains.annotations.Nullable;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.WebRequest;

import java.util.Map;

@RequiredArgsConstructor
@DgsComponent
public class EshopContextBuilder implements DgsCustomContextBuilderWithRequest<EshopContext> {

  private final UserService userService;

  @Override
  public EshopContext build(
    @Nullable Map<String, ?> map,
    @Nullable HttpHeaders httpHeaders,
    @Nullable WebRequest webRequest
  ) {
    return userService.getUser()
      .map(EshopContext::new)
      .orElse(null);
  }
}
