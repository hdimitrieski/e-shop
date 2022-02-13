import { AuthProviderProps } from 'oidc-react/build/src/AuthContextInterface';

export const oidcConfig: AuthProviderProps = {
  onSignIn: () => {
    window.location.replace('/');
  },
  authority: `${process.env.REACT_APP_AUTH_SERVER_URL}/auth/realms/e-shop`,
  clientId: 'eshop-spa',
  redirectUri: window.location.origin + '/',
  scope: 'openid profile email basket orders webshoppingagg order.notifications',
  responseType: 'code',
  autoSignIn: true,
  loadUserInfo: true,
  automaticSilentRenew: true
};
