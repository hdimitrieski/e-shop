package com.eshop.catalog.config;

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
        .mvcMatchers(HttpMethod.GET, "/catalog/*").permitAll()
        .mvcMatchers(HttpMethod.POST, "/catalog/*").hasAuthority("SCOPE_catalog") //.access("hasAuthority('SCOPE_catalog.write')")
        .mvcMatchers(HttpMethod.PUT, "/catalog/*").hasAuthority("SCOPE_catalog")
        .mvcMatchers(HttpMethod.DELETE, "/catalog/*").hasAuthority("SCOPE_catalog")
        .and()
        .oauth2ResourceServer()
        .jwt();

    return http.build();
  }

}
