import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  // When start background with docker
  // issuer: 'https://authorization-service:8443/auth/realms/e-shop',
  // When start background on localhost
  issuer: 'http://localhost:8080/auth/realms/e-shop',

  // URL of the SPA to redirect the user to after login
  redirectUri: window.location.origin + '/',

  // The SPA's id. The SPA is registered with this id at the auth-server
  clientId: 'eshop-spa',

  // Just needed if your auth server demands a secret. In general, this
  // is a sign that the auth server is not configured with SPAs in mind
  // and it might not enforce further best practices vital for security
  // such applications.
  // dummyClientSecret: 'secret',

  responseType: 'code',

  // set the scope for the permissions the client should request
  // The first two are defined by OIDC.
  // Important: Request offline_access to get a refresh token
  scope: 'openid profile email basket orders webshoppingagg order.notifications',

  // offline_access is not needed for silent refresh

  // This is needed for silent refresh (refreshing tokens w/o a refresh_token)
  // **AND** for logging in with a popup
  // silentRefreshRedirectUri: `${window.location.origin}/assets/silent-refresh.html`,

  // useSilentRefresh: true,
  showDebugInformation: false,
  sessionChecksEnabled: true,
  requireHttps: false,

  /**
   * Defines whether every url provided by the discovery
   * document has to start with the issuer's url.
   */
  strictDiscoveryDocumentValidation: false,

  /**
   * Defined whether to skip the validation of the issuer in the discovery document.
   * Normally, the discovey document's url starts with the url of the issuer.
   */
  skipIssuerCheck: true
};
