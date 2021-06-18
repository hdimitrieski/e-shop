package com.eshop.catalog.config;

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

  @Value("${app.security.audience.catalog}")
  private String catalogAudience;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .mvcMatcher("/catalog/**")
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/catalog/*").permitAll()
        .mvcMatchers(HttpMethod.POST, "/catalog/*").hasRole("admin")
        .mvcMatchers(HttpMethod.PUT, "/catalog/*").hasRole("admin")
        .mvcMatchers(HttpMethod.DELETE, "/catalog/*").hasRole("admin")
        .and()
        .oauth2ResourceServer()
        .jwt()
        .decoder(new EshopJwtDecoder(issuer, catalogAudience))
        .jwtAuthenticationConverter(new EshopJwtAuthenticationConverter(userNameAttribute));

    return http.build();
  }

}
