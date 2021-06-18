package com.eshop.analytics.config;

import com.eshop.security.EshopJwtAuthenticationConverter;
import com.eshop.security.EshopJwtDecoder;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

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
        .antMatchers("/analytics/products/**").hasAnyAuthority("ROLE_admin", "SCOPE_analytics-products")
        .anyRequest().hasRole("admin")
        .and()
        .oauth2ResourceServer()
        .jwt()
        .decoder(new EshopJwtDecoder(issuer, analyticsAudience))
        .jwtAuthenticationConverter(new EshopJwtAuthenticationConverter(userNameAttribute));
  }

}
