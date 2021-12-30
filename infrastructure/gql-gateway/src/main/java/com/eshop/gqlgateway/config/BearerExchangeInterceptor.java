package com.eshop.gqlgateway.config;

import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.core.AbstractOAuth2Token;

import java.io.IOException;

public class BearerExchangeInterceptor implements ClientHttpRequestInterceptor {
  @Override
  public ClientHttpResponse intercept(
    HttpRequest request,
    byte[] body,
    ClientHttpRequestExecution execution
  ) throws IOException {
    AbstractOAuth2Token token = (AbstractOAuth2Token) SecurityContextHolder.getContext().getAuthentication().getCredentials();
    request.getHeaders().setBearerAuth(token.getTokenValue());
    return execution.execute(request, body);
  }
}
