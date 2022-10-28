package com.eshop.discovery.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@Order(1)
public class SecurityConfig {

  @Autowired
  public void configureGlobal(AuthenticationManagerBuilder auth, PasswordEncoder passwordEncoder) throws Exception {
    auth.inMemoryAuthentication()
      .withUser("user")
      .password(passwordEncoder.encode("password"))
      .roles("SYSTEM")
      .and()
      .withUser("admin")
      .password(passwordEncoder.encode("admin"))
      .roles("ADMIN");
  }

  @Bean
  SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    return http
      .csrf().disable()
      .requestMatchers()
      .antMatchers(HttpMethod.GET, "/")
      .and()
      .authorizeRequests()
      .antMatchers("/").hasRole("ADMIN")
      .and()
      .requestMatchers()
      .antMatchers("/eureka/**")
      .and()
      .authorizeRequests()
      .antMatchers(HttpMethod.GET).hasAnyRole("ADMIN", "SYSTEM")
      .antMatchers(HttpMethod.POST).hasRole("SYSTEM")
      .antMatchers(HttpMethod.PUT).hasRole("SYSTEM")
      .antMatchers(HttpMethod.DELETE).hasRole("SYSTEM")
      .and()
      .requestMatchers()
      .antMatchers("/actuator/**")
      .and()
      .authorizeRequests()
      .antMatchers("/actuator/**").authenticated()
      .anyRequest().denyAll()
      .and()
      .httpBasic()
      .and()
      .build();
  }

}
