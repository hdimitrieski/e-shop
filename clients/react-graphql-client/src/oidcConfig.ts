export const oidcConfig = {
  onSignIn: () => {
    window.location.replace('/');
  },
  authority: `${process.env.REACT_APP_AUTH_SERVER_URL}/auth/realms/e-shop`,
  clientId: 'eshop-spa',
  redirectUri: window.location.origin + '/',
  scope: 'openid profile email basket orders webshoppingagg order.notifications',
  responseType: 'code',
  autoSignIn: false,
  loadUserInfo: true,
  automaticSilentRenew: true
};
