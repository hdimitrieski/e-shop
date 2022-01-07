import { AuthConfig } from 'angular-oauth2-oidc';

export const authCodeFlowConfig: AuthConfig = {
  issuer: 'http://localhost:8090/auth/realms/e-shop',

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
};
