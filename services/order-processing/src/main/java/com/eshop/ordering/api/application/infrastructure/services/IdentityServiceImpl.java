package com.eshop.ordering.api.application.infrastructure.services;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class IdentityServiceImpl implements IdentityService {
  @Override
  public String getUserIdentity() {
    return SecurityContextHolder.getContext().getAuthentication().getName();
  }

  @Override
  public String getUserName() {
    var user = (UserDetails) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    return user.getUsername();
  }
}
