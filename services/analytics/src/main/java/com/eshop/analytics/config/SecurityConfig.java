package com.eshop.analytics.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import com.eshop.security.EshopJwtDecoder;
import com.eshop.security.EshopRole;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

import static com.eshop.security.GrantedAuthoritiesUtils.role;
import static com.eshop.security.GrantedAuthoritiesUtils.scope;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

  private static final String PRODUCTS_ANALYTICS_SCOPE = "analytics-products";

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Value("${app.auth-server.issuer-uri}")
  private String issuer;

  @Value("${app.security.audience.analytics}")
  private String analyticsAudience;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .authorizeRequests()
        .antMatchers("/analytics/products/**")
          .hasAnyAuthority(role(EshopRole.Admin), scope(PRODUCTS_ANALYTICS_SCOPE))
        .anyRequest().hasRole(EshopRole.Admin)
        .and()
        .oauth2ResourceServer()
        .jwt()
        .decoder(jwtDecoder())
        .jwtAuthenticationConverter(jwtAuthenticationConverter());
  }

  private JwtDecoder jwtDecoder() {
    return new EshopJwtDecoder(issuer, analyticsAudience);
  }

  private Converter<Jwt, JwtAuthenticationToken> jwtAuthenticationConverter() {
    return new EshopJwtAuthenticationConverter(userNameAttribute);
  }

}
