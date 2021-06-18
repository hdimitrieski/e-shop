package com.eshop.ordering.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import com.eshop.security.EshopJwtDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

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
        .mvcMatchers("/orders/ship").hasRole("admin")
        .mvcMatchers(HttpMethod.GET, "/orders/*").hasAuthority("SCOPE_orders")
        .mvcMatchers(HttpMethod.POST, "/orders/*").hasAuthority("SCOPE_orders")
        .mvcMatchers(HttpMethod.PUT, "/orders/*").hasAuthority("SCOPE_orders")
        .mvcMatchers(HttpMethod.DELETE, "/orders/*").hasAuthority("SCOPE_orders")
        .and()
        .oauth2ResourceServer()
        .jwt()
        .decoder(new EshopJwtDecoder(issuer, orderAudience))
        .jwtAuthenticationConverter(new EshopJwtAuthenticationConverter(userNameAttribute));

    return http.build();
  }

}
