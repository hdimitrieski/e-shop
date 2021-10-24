package com.eshop.ordering.api.infrastructure.services;

import com.eshop.ordering.api.application.services.IdentityService;
import com.eshop.ordering.shared.ApplicationService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

@ApplicationService
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
