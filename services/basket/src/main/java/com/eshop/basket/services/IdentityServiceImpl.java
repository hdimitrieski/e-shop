package com.eshop.basket.services;

import org.springframework.stereotype.Service;

// TODO HD implement after auth server is ready
@Service
public class IdentityServiceImpl implements IdentityService {
  @Override
  public String getUserIdentity() {
    return "user-id-1";
  }
}
