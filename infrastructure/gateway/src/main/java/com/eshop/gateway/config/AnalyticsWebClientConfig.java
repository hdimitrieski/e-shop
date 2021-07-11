package com.eshop.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.cloud.loadbalancer.annotation.LoadBalancerClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.oauth2.client.registration.ReactiveClientRegistrationRepository;
import org.springframework.security.oauth2.client.web.reactive.function.client.ServerOAuth2AuthorizedClientExchangeFilterFunction;
import org.springframework.security.oauth2.client.web.server.ServerOAuth2AuthorizedClientRepository;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
@LoadBalancerClient(value = "analytics", configuration = RoundRobinLoadBalancerConfig.class)
public class AnalyticsWebClientConfig {

  @Value("${app.security.oauth2.client.analytics.id}")
  private String analyticsClientId;

  @LoadBalanced
  @Bean
  public WebClient.Builder analyticsWebClient(
      ReactiveClientRegistrationRepository clientRegistrations,
      ServerOAuth2AuthorizedClientRepository authorizedClients
  ) {
    final var oAuth = new ServerOAuth2AuthorizedClientExchangeFilterFunction(clientRegistrations, authorizedClients);
    oAuth.setDefaultClientRegistrationId(analyticsClientId);
    return WebClient.builder()
        .filter(oAuth);
  }

}
