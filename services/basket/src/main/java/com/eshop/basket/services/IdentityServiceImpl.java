package com.eshop.basket.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class IdentityServiceImpl implements IdentityService {
  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Override
  public String getUserIdentity() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public String getUserName() {
    var token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return token.getClaims().get(userNameAttribute).toString();
  }
}
