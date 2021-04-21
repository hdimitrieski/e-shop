package com.eshop.basket.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationConverter;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;

@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {
  Logger logger = LoggerFactory.getLogger(SecurityConfig.class);

  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Override
  protected void configure(HttpSecurity http) throws Exception {
    http
        .antMatcher("/basket/**")
        .authorizeRequests()
        .antMatchers(HttpMethod.GET, "/basket/*").hasAuthority("SCOPE_basket")
        .antMatchers(HttpMethod.POST, "/basket/*").hasAuthority("SCOPE_basket")
        .antMatchers(HttpMethod.PUT, "/basket/*").hasAuthority("SCOPE_basket")
        .antMatchers(HttpMethod.DELETE, "/basket/*").hasAuthority("SCOPE_basket")
        .and()
        .oauth2ResourceServer()
        .jwt(jwtSpec -> jwtSpec.jwtAuthenticationConverter(jwt -> {
          var token = new JwtAuthenticationConverter().convert(jwt);
          return new JwtAuthenticationToken(jwt,
              token.getAuthorities(), jwt.getClaim(userNameAttribute));
        }));
  }

  @EventListener
  public void authenticationSuccess(AuthenticationSuccessEvent event) {
    logger.info("User {} authenticated", event.getAuthentication().getName());
  }

}
