import { Button } from 'react-bootstrap';
import { useAuth } from 'oidc-react';
import { useNavigate } from 'react-router-dom';

export const LoginButton = () => {
  const auth = useAuth();
  const navigate = useNavigate();
  const loggedIn = !!auth.userData;

  const login = () => {
    if (!loggedIn) {
      auth.signIn();
    }
  };

  const logout = () => {
    if (loggedIn) {
      auth.signOut().then(() => navigate('/'));
    }
  };

  return loggedIn
    ? <Button onClick={logout}>Logout</Button>
    : <Button onClick={login}>Login</Button>;
};
