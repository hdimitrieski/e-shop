package com.eshop.gqlgateway.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.method.configuration.GlobalMethodSecurityConfiguration;

@EnableGlobalMethodSecurity(
  securedEnabled = true
)
@Configuration
public class MethodSecurityConfig extends GlobalMethodSecurityConfiguration {
}
