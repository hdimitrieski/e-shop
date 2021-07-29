package com.eshop.security;

import org.springframework.lang.NonNull;
import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;
import org.springframework.util.Assert;

/**
 * JWT decoder that ensures that both audience and issuer claims are valid.
 */
public class EshopJwtDecoder implements JwtDecoder {
  private final String issuer;
  private final String allowedAudience;

  public EshopJwtDecoder(@NonNull String issuer, @NonNull String allowedAudience) {
    Assert.hasText(issuer, "Issuer cannot be empty");
    Assert.hasText(allowedAudience, "Audience cannot be empty");
    this.issuer = issuer;
    this.allowedAudience = allowedAudience;
  }

  @Override
  public Jwt decode(String token) throws JwtException {
    NimbusJwtDecoder decoder = JwtDecoders.fromOidcIssuerLocation(issuer);
    decoder.setJwtValidator(delegatingValidator());
    return decoder.decode(token);
  }

  private OAuth2TokenValidator<Jwt> delegatingValidator() {
    return new DelegatingOAuth2TokenValidator<>(
        JwtValidators.createDefaultWithIssuer(issuer),
        new EshopAudienceValidator(allowedAudience)
    );
  }


}
