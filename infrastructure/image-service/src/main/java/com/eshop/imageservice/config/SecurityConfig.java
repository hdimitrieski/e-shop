package com.eshop.imageservice.config;

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

@Configuration
public class SecurityConfig {

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Value("${app.auth-server.issuer-uri}")
  private String issuer;

  @Value("${app.security.audience.image}")
  private String imageAudience;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .cors()
        .and()
        .csrf().disable()
        .mvcMatcher("/**")
        .authorizeRequests()
        .anyRequest().hasRole(EshopRole.Admin)
        .and()
        .oauth2ResourceServer()
        .jwt()
        .decoder(jwtDecoder())
        .jwtAuthenticationConverter(jwtAuthenticationConverter());

    return http.build();
  }

  private JwtDecoder jwtDecoder() {
    return new EshopJwtDecoder(issuer, imageAudience);
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
