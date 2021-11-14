package com.eshop.rating.config;

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
        .mvcMatcher("/rating/**")
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/rating/*").permitAll();

    return http.build();
  }

}
