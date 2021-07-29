package com.eshop.security;

import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.OAuth2Error;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidatorResult;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.util.Assert;

/**
 * JWT Validator that checks if the token contains the needed audience.
 */
class EshopAudienceValidator implements OAuth2TokenValidator<Jwt> {

  private final String audience;

  public EshopAudienceValidator(@NonNull String audience) {
    Assert.hasText(audience, "Audience cannot be empty");
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
