package com.eshop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

@EnableWebFluxSecurity
public class SecurityConfig {
  // TODO HD read https://spring.io/guides/gs/gateway/
  // TODO HD spring boot webflux security samples: https://github.com/spring-projects/spring-security/tree/5.4.5/samples/boot
  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    /*
     By convention, OAuth 2.0 scopes should be prefixed with SCOPE_
     when checked for authority using Spring Security.
    */
    http.csrf().disable()
        .authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/api/v1/catalog/*").permitAll()
        .pathMatchers("/api/v1/basket/*").hasAuthority("SCOPE_webshoppingagg")
        .pathMatchers("/api/v1/orders/*").hasAuthority("SCOPE_webshoppingagg")
        .pathMatchers( "/api/v1/catalog/*").hasAuthority("SCOPE_webshoppingagg")
        .anyExchange().authenticated()
        .and()
        .oauth2Client()
        .and()
        .oauth2ResourceServer()
        .jwt();

    return http.build();
  }

}
