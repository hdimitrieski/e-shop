package com.eshop.gateway.config;

import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@EnableWebFluxSecurity
public class SecurityConfig {
  // TODO HD read https://spring.io/guides/gs/gateway/
  // TODO HD spring boot webflux security samples: https://github.com/spring-projects/spring-security/tree/5.4.5/samples/boot
  // TODO HD maybe i will need the WebClient directly to implement the API gateway. I have something similar
  // here https://github.com/hdimitrieski/microservice-examples/blob/master/clientserver/src/main/java/com/example/config/WebClientConfig.java
  @Bean
  SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
    /*
     By convention, OAuth 2.0 scopes should be prefixed with SCOPE_
     when checked for authority using Spring Security.
    */
    http.csrf().disable()
        .authorizeExchange((exchanges) ->
            exchanges
//                .pathMatchers("/", "/public/**").permitAll()
                .anyExchange().authenticated()
        )
        .oauth2Login(withDefaults())
        .oauth2Client(withDefaults());


    // TODO hd redirectUri cannot be empty
//    http.authorizeExchange()
//        .pathMatchers("/actuator/**").permitAll()
//        .pathMatchers(POST, baseUri).hasAuthority("SCOPE_product:write")
//        .pathMatchers(DELETE, baseUri).hasAuthority("SCOPE_product:write")
//        .pathMatchers(GET, baseUri).hasAuthority("SCOPE_product:read")
//        // Ensures that the user is authenticated before being allowed access to all other URLs
//        .anyExchange().authenticated()
//        .and()
//        /*
//         1. specifies that authentication and authorization will be based on
//         a JWT-encoded OAuth 2.0 access token
//         2. The endpoint of the authorization server's jwk-set endpoint has been
//         registered in the configuration file, store.yml
//        */
//        .oauth2Login(oauth2Login ->
//            oauth2Login.loginPage("/oauth2/authorization/messaging-client-oidc"))
//        .oauth2Client(withDefaults()
//        );

    return http.build();
  }

  @Bean
  MapReactiveUserDetailsService userDetailsService() {
    UserDetails userDetails = User.withDefaultPasswordEncoder()
        .username("user2")
        .password("password2")
        .roles("uaa.user")
        .build();
    return new MapReactiveUserDetailsService(userDetails);
  }

}
