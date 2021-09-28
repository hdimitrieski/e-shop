package com.eshop.catalogquery.config;

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
        .mvcMatcher("/catalog/**")
        .authorizeRequests()
        .mvcMatchers(HttpMethod.GET, "/catalog/*").permitAll();

    return http.build();
  }

}
