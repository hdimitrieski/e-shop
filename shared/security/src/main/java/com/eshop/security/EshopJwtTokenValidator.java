package com.eshop.security;

import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;

class EshopJwtTokenValidator implements OAuth2TokenValidator<Jwt> {

  private final String audience;

  public EshopJwtTokenValidator(String audience) {
    this.audience = audience;
  }

  @Override
  public OAuth2TokenValidatorResult validate(Jwt token) {
    return containsValidAudience(token)
        ? OAuth2TokenValidatorResult.success()
        : OAuth2TokenValidatorResult.failure(invalidTokenError());
  }

  private boolean containsValidAudience(Jwt token) {
    return token.getAudience().contains(audience);
  }

  private OAuth2Error invalidTokenError() {
    return new OAuth2Error("invalid_token", "Expected token audience: %s".formatted(audience), null);
  }

}
