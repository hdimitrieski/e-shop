package com.eshop.ordering.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import com.eshop.security.EshopJwtDecoder;
import com.eshop.security.EshopRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import static com.eshop.security.GrantedAuthoritiesUtils.scope;

@Configuration
public class SecurityConfig {
  private static final String ORDERS_SCOPE = "orders";

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Value("${app.auth-server.issuer-uri}")
  private String issuer;

  @Value("${app.security.audience.order}")
  private String orderAudience;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .mvcMatcher("/orders/**")
      .authorizeRequests()
      .antMatchers("/orders/ship").hasRole(EshopRole.Admin)
      .antMatchers("/orders/cancel").hasRole(EshopRole.Admin)
      .antMatchers(HttpMethod.GET, "/orders").hasRole(EshopRole.Admin)
      .antMatchers(HttpMethod.GET, "/orders/*").hasAuthority(scope(ORDERS_SCOPE))
      .antMatchers(HttpMethod.POST, "/orders/*").hasAuthority(scope(ORDERS_SCOPE))
      .antMatchers(HttpMethod.PUT, "/orders/*").hasAuthority(scope(ORDERS_SCOPE))
      .antMatchers(HttpMethod.DELETE, "/orders/*").hasAuthority(scope(ORDERS_SCOPE))
      .and()
      .oauth2ResourceServer()
      .jwt()
      .decoder(jwtDecoder())
      .jwtAuthenticationConverter(jwtAuthenticationConverter());

    return http.build();
  }

  private JwtDecoder jwtDecoder() {
    return new EshopJwtDecoder(issuer, orderAudience);
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
