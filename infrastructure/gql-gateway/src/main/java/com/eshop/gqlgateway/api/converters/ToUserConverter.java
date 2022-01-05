package com.eshop.gqlgateway.api.converters;

import com.eshop.gqlgateway.models.UserDto;
import com.eshop.gqlgateway.types.User;
import org.springframework.stereotype.Component;

@Component
public class ToUserConverter {
  public User convert(UserDto userDto) {
    return User.newBuilder()
      .userName(userDto.userName())
      .email(userDto.email())
      .build();
  }
}
