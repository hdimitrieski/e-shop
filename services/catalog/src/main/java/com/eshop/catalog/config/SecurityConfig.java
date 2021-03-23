package com.eshop.catalog.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    http
        .mvcMatcher("/catalog/**")
        .authorizeRequests()
        .mvcMatchers("/catalog/items").access("hasAuthority('SCOPE_resource.read')")
        .and()
        .oauth2ResourceServer()
        .jwt();

    return http.build();
  }

//  @Bean
//  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) throws Exception {
//    http
//        .authorizeExchange()
//        .pathMatchers("/catalog/items")
//        .hasAuthority("SCOPE_resource.read")
//        .anyExchange()
//        .authenticated()
//        .and()
//        .oauth2ResourceServer()
//        .jwt();
//    return http.build();
//  }
}
