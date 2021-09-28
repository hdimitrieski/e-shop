package com.eshop.catalog.config;

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
        .mvcMatchers(HttpMethod.POST, "/catalog/*").hasRole(EshopRole.Admin)
        .mvcMatchers(HttpMethod.PUT, "/catalog/*").hasRole(EshopRole.Admin)
        .mvcMatchers(HttpMethod.DELETE, "/catalog/*").hasRole(EshopRole.Admin)
        .and()
        .oauth2ResourceServer()
        .jwt()
        .decoder(jwtDecoder())
        .jwtAuthenticationConverter(jwtAuthenticationConverter());

    return http.build();
  }

  private JwtDecoder jwtDecoder() {
    return new EshopJwtDecoder(issuer, catalogAudience);
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
