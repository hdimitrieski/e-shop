package com.eshop.rating.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import com.eshop.security.EshopJwtDecoder;
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
  public static final String SCOPE_PREFIX = "SCOPE";

  private static final String RATING_SCOPE = "rating";

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Value("${app.auth-server.issuer-uri}")
  private String issuer;

  @Value("${app.security.audience.rating}")
  private String ratingAudience;
  // TODO HD what happens if the user is not logged in? He wouldn't be able to see the reviews.
  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
      .mvcMatcher("/rating/**")
      .authorizeRequests()
      .mvcMatchers(HttpMethod.GET, "/rating/*").permitAll()
      .mvcMatchers(HttpMethod.POST, "/rating/*").hasAuthority(scope(RATING_SCOPE))
      .and()
      .oauth2ResourceServer()
      .jwt()
      .decoder(jwtDecoder())
      .jwtAuthenticationConverter(jwtAuthenticationConverter());

    return http
      .cors().disable()
      .csrf().disable()
      .build();
  }

  private JwtDecoder jwtDecoder() {
    return new EshopJwtDecoder(issuer, ratingAudience);
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

  private String scope(String scopeName) {
    return "%s_%s".formatted(SCOPE_PREFIX, scopeName);
  }

}
