package com.eshop.gqlgateway.infrastructure;

import com.eshop.gqlgateway.models.UserDto;
import com.eshop.gqlgateway.services.UserService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {
  @Value("${app.security.jwt.user-name-attribute}")
  private String userNameAttribute;

  @Override
  public Optional<UserDto> getUser() {
    var principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    if (principal instanceof Jwt) {
      var jwt = (Jwt) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
      return Optional.of(userFrom(jwt));
    }
    return Optional.empty();
  }

  private UserDto userFrom(Jwt jwt) {
    return new UserDto(userNameFrom(jwt), emailFrom(jwt));
  }

  private String userNameFrom(Jwt jwt) {
    return jwt.getClaims().get(userNameAttribute).toString();
  }

  private String emailFrom(Jwt jwt) {
    return jwt.getClaims().get("email").toString();
  }
}
