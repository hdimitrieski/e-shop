package com.eshop.ordering.api.infrastructure.services;

import com.eshop.ordering.api.application.services.IdentityService;
import com.eshop.ordering.shared.ApplicationService;
import com.eshop.security.EshopRole;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;

import static com.eshop.security.GrantedAuthoritiesUtils.role;

@ApplicationService
public class IdentityServiceImpl implements IdentityService {

  private final GrantedAuthority ADMIN = new SimpleGrantedAuthority(role(EshopRole.Admin));

  @Override
  public String getUserIdentity() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public boolean isAdmin() {
    return authentication().getAuthorities().stream()
      .anyMatch(grantedAuthority -> grantedAuthority.equals(ADMIN));
  }

  private Jwt currentJwt() {
    return (Jwt) authentication().getPrincipal();
  }

  private Authentication authentication() {
    return SecurityContextHolder.getContext().getAuthentication();
  }
}
