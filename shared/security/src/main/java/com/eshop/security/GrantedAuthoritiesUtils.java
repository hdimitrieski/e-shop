package com.eshop.security;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class GrantedAuthoritiesUtils {

  public static final String SCOPE_PREFIX = "SCOPE";
  public static final String ROLE_PREFIX = "ROLE";

  public static String scope(String scopeName) {
    return "%s_%s".formatted(SCOPE_PREFIX, scopeName);
  }

  public static String role(String roleName) {
    return "%s_%s".formatted(ROLE_PREFIX, roleName);
  }

}
