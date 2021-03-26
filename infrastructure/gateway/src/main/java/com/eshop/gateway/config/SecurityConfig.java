package com.eshop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
public class SecurityConfig {
  // TODO HD read https://spring.io/guides/gs/gateway/
  // TODO HD spring boot webflux security samples: https://github.com/spring-projects/spring-security/tree/5.4.5/samples/boot
  // TODO HD maybe i will need the WebClient directly to implement the API gateway. I have something similar
  // here https://github.com/hdimitrieski/microservice-examples/blob/master/clientserver/src/main/java/com/example/config/WebClientConfig.java
  // TODO HD do I need to make the gateway resource server as well ?
  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    /*
     By convention, OAuth 2.0 scopes should be prefixed with SCOPE_
     when checked for authority using Spring Security.
    */
//    http.csrf().disable()
//        .authorizeExchange((exchanges) ->
//                exchanges
//                    // .pathMatchers("/actuator/**").permitAll()
//                    .pathMatchers(HttpMethod.GET, "/catalog/*").permitAll()
//                    .pathMatchers(HttpMethod.POST, "/catalog/*").hasAuthority("SCOPE_catalog.write") //.access("hasAuthority('SCOPE_catalog.write')")
//                    .pathMatchers(HttpMethod.PUT, "/catalog/*").hasAuthority("SCOPE_catalog.write")
//                    .pathMatchers(HttpMethod.DELETE, "/catalog/*").hasAuthority("SCOPE_catalog.write")
//                    .pathMatchers(HttpMethod.GET, "/basket/*").hasAuthority("SCOPE_basket.read")
//                    .pathMatchers(HttpMethod.POST, "/basket/*").hasAuthority("SCOPE_basket.write")
//                    .pathMatchers(HttpMethod.PUT, "/basket/*").hasAuthority("SCOPE_basket.write")
//                    .pathMatchers(HttpMethod.DELETE, "/basket/*").hasAuthority("SCOPE_basket.write")
//                    .pathMatchers(HttpMethod.GET, "/orders/*").hasAuthority("SCOPE_order.read")
//                    .pathMatchers(HttpMethod.POST, "/orders/*").hasAuthority("SCOPE_order.write")
//                    .pathMatchers(HttpMethod.PUT, "/orders/*").hasAuthority("SCOPE_order.write")
//                    .pathMatchers(HttpMethod.DELETE, "/orders/*").hasAuthority("SCOPE_order.write")
////                .pathMatchers("/", "/public/**").permitAll()
//                .anyExchange().authenticated()
//        )
//        .oauth2Login(withDefaults())
//        .oauth2Client(withDefaults());

    http.csrf().disable()
        .authorizeExchange()
        .pathMatchers(HttpMethod.GET, "/catalog/*").permitAll()
        .pathMatchers(HttpMethod.POST, "/catalog/*").hasAuthority("SCOPE_catalog.write") //.access("hasAuthority('SCOPE_catalog.write')")
        .pathMatchers(HttpMethod.PUT, "/catalog/*").hasAuthority("SCOPE_catalog.write")
        .pathMatchers(HttpMethod.DELETE, "/catalog/*").hasAuthority("SCOPE_catalog.write")
        .pathMatchers(HttpMethod.GET, "/basket/*").hasAuthority("SCOPE_basket.read")
        .pathMatchers(HttpMethod.POST, "/basket/*").hasAuthority("SCOPE_basket.write")
        .pathMatchers(HttpMethod.PUT, "/basket/*").hasAuthority("SCOPE_basket.write")
        .pathMatchers(HttpMethod.DELETE, "/basket/*").hasAuthority("SCOPE_basket.write")
        .pathMatchers(HttpMethod.GET, "/orders/*").hasAuthority("SCOPE_order.read")
        .pathMatchers(HttpMethod.POST, "/orders/*").hasAuthority("SCOPE_order.write")
        .pathMatchers(HttpMethod.PUT, "/orders/*").hasAuthority("SCOPE_order.write")
        .pathMatchers(HttpMethod.DELETE, "/orders/*").hasAuthority("SCOPE_order.write")
        .anyExchange().authenticated()
//        .anyExchange().permitAll()
        .and()
        .oauth2Login(withDefaults())
        .oauth2Client(withDefaults());

    return http.build();
  }

}
