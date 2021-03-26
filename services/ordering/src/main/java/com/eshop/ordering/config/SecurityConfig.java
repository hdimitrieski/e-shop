package com.eshop.ordering.config;

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
        .mvcMatcher("/orders/**")
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/orders/*").hasAuthority("SCOPE_order.read")
        .mvcMatchers(HttpMethod.POST, "/orders/*").hasAuthority("SCOPE_order.write")
        .mvcMatchers(HttpMethod.PUT, "/orders/*").hasAuthority("SCOPE_order.write")
        .mvcMatchers(HttpMethod.DELETE, "/orders/*").hasAuthority("SCOPE_order.write")
        .and()
        .oauth2ResourceServer()
        .jwt();

    return http.build();
  }

}
