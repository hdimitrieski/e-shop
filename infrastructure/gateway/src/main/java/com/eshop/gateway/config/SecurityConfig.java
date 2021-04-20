package com.eshop.gateway.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@Configuration
public class SecurityConfig {

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    http
        .cors()
        .and()
        .csrf().disable()
        .authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/api/v1/catalog/*").permitAll()
        // By convention, OAuth 2.0 scopes should be prefixed with SCOPE_ when checked for authority using Spring Security.
        .pathMatchers("/api/v1/basket/*").hasAuthority("SCOPE_webshoppingagg")
        .pathMatchers("/api/v1/orders/*").hasAuthority("SCOPE_webshoppingagg")
        .pathMatchers("/api/v1/catalog/*").hasAuthority("SCOPE_webshoppingagg")
        .anyExchange().authenticated()
        .and()
        .oauth2ResourceServer()
        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwt -> {
          var token = new JwtAuthenticationConverter().convert(jwt);
          return Mono.just(new JwtAuthenticationToken(jwt,
              token.getAuthorities(), jwt.getClaim(userNameAttribute)));
        }));

    return http.build();
  }

}
