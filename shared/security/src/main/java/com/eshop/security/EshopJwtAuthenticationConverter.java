package com.eshop.security;

import org.jetbrains.annotations.NotNull;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.security.oauth2.server.resource.authentication.JwtGrantedAuthoritiesConverter;

import java.util.Collection;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class EshopJwtAuthenticationConverter implements Converter<Jwt, JwtAuthenticationToken> {
  private static final String ROLES_CLAIM_NAME = "roles";
  private final String userNameAttribute;

  public EshopJwtAuthenticationConverter(String userNameAttribute) {
    this.userNameAttribute = userNameAttribute;
  }

  @Override
  public JwtAuthenticationToken convert(@NotNull Jwt jwt) {
    return new JwtAuthenticationToken(jwt, extractGrantedAuthorities(jwt), jwt.getClaim(userNameAttribute));
  }

  private Collection<GrantedAuthority> extractGrantedAuthorities(Jwt jwt) {
    return Stream.concat(extractScopes(jwt).stream(), extractRoles(jwt).stream())
        .collect(Collectors.toSet());
  }

  private Collection<GrantedAuthority> extractScopes(Jwt jwt) {
    return new JwtGrantedAuthoritiesConverter().convert(jwt);
  }

  private Collection<GrantedAuthority> extractRoles(Jwt jwt) {
    final var converter = new JwtGrantedAuthoritiesConverter();
    converter.setAuthoritiesClaimName(ROLES_CLAIM_NAME);
    converter.setAuthorityPrefix("");
    return converter.convert(jwt);
  }
}
