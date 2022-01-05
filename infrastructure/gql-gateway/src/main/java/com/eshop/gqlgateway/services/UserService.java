package com.eshop.gqlgateway.services;

import com.eshop.gqlgateway.models.UserDto;

import java.util.Optional;

public interface UserService {
  Optional<UserDto> getUser();
}
