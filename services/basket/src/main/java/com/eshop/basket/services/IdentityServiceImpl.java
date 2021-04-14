package com.eshop.basket.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

@Service
public class IdentityServiceImpl implements IdentityService {
  @Override
  public String getUserIdentity() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public String getUserName() {
    var token = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return token.getClaims().get("preferred_username").toString();
  }
}
