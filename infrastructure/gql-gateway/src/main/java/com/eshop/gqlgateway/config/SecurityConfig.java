package com.eshop.gqlgateway.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {
  // By convention, OAuth 2.0 scopes should be prefixed with SCOPE_ when checked for authority using Spring Security.
  private static final String GATEWAY_SCOPE = "SCOPE_webshoppingagg";

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .cors()
      .and()
      .csrf().disable()
      .oauth2ResourceServer(c -> c
        .jwt()
        .jwtAuthenticationConverter(jwtAuthenticationConverter())
      )
      .build();
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
