package com.eshop.gqlgateway.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  // By convention, OAuth 2.0 scopes should be prefixed with SCOPE_ when checked for authority using Spring Security.
  private static final String GATEWAY_SCOPE = "SCOPE_webshoppingagg";

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
      .cors()
      .and()
      .csrf().disable()
      .oauth2ResourceServer()
      .jwt()
      .jwtAuthenticationConverter(jwtAuthenticationConverter());
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
