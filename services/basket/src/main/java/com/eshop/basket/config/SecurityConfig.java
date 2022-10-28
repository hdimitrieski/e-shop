package com.eshop.basket.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import com.eshop.security.EshopJwtDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.web.SecurityFilterChain;

import static com.eshop.security.GrantedAuthoritiesUtils.scope;

@Configuration
public class SecurityConfig {
  private static final Logger logger = LoggerFactory.getLogger(SecurityConfig.class);
  private static final String BASKET_SCOPE = "basket";

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Value("${app.auth-server.issuer-uri}")
  private String issuer;

  @Value("${app.security.audience.basket}")
  private String basketAudience;

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .antMatcher("/basket/**")
      .authorizeRequests()
      .antMatchers(HttpMethod.GET, "/basket/*").hasAuthority(scope(BASKET_SCOPE))
      .antMatchers(HttpMethod.POST, "/basket/*").hasAuthority(scope(BASKET_SCOPE))
      .antMatchers(HttpMethod.PUT, "/basket/*").hasAuthority(scope(BASKET_SCOPE))
      .antMatchers(HttpMethod.DELETE, "/basket/*").hasAuthority(scope(BASKET_SCOPE))
      .and()
      .oauth2ResourceServer(c -> c
        .jwt()
        .decoder(jwtDecoder())
        .jwtAuthenticationConverter(jwtAuthenticationConverter())
      )
      .build();
  }

  @EventListener
  public void authenticationSuccess(AuthenticationSuccessEvent event) {
    logger.info("User {} authenticated", event.getAuthentication().getName());
  }

  private JwtDecoder jwtDecoder() {
    return new EshopJwtDecoder(issuer, basketAudience);
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
