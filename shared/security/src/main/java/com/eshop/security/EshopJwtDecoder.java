package com.eshop.security;

import org.springframework.security.oauth2.core.DelegatingOAuth2TokenValidator;
import org.springframework.security.oauth2.core.OAuth2TokenValidator;
import org.springframework.security.oauth2.jwt.*;

public class EshopJwtDecoder implements JwtDecoder {
  private final String issuer;
  private final String allowedAudience;

  public EshopJwtDecoder(String issuer, String allowedAudience) {
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
        new EshopJwtTokenValidator(allowedAudience)
    );
  }


}
