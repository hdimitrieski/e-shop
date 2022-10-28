package com.eshop.analytics.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import com.eshop.security.EshopJwtDecoder;
import com.eshop.security.EshopRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import static com.eshop.security.GrantedAuthoritiesUtils.role;
import static com.eshop.security.GrantedAuthoritiesUtils.scope;

@Configuration
public class SecurityConfig {

  private static final String PRODUCTS_ANALYTICS_SCOPE = "analytics-products";

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Value("${app.auth-server.issuer-uri}")
  private String issuer;

  @Value("${app.security.audience.analytics}")
  private String analyticsAudience;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .authorizeRequests()
      .antMatchers("/analytics/products/**")
      .hasAnyAuthority(role(EshopRole.Admin), scope(PRODUCTS_ANALYTICS_SCOPE))
      .anyRequest().hasRole(EshopRole.Admin)
      .and()
      .oauth2ResourceServer(c -> c
        .jwt()
        .decoder(jwtDecoder())
        .jwtAuthenticationConverter(jwtAuthenticationConverter())
      )
      .build();
  }

  private JwtDecoder jwtDecoder() {
    return new EshopJwtDecoder(issuer, analyticsAudience);
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
