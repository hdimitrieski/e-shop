package com.eshop.basket.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .mvcMatcher("/basket/**")
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/basket/*").hasAuthority("SCOPE_basket")
        .mvcMatchers(HttpMethod.POST, "/basket/*").hasAuthority("SCOPE_basket")
        .mvcMatchers(HttpMethod.PUT, "/basket/*").hasAuthority("SCOPE_basket")
        .mvcMatchers(HttpMethod.DELETE, "/basket/*").hasAuthority("SCOPE_basket")
        .and()
        .oauth2ResourceServer()
        .jwt();

    return http.build();
  }

}
