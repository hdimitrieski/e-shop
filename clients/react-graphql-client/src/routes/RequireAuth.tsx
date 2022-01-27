import React from 'react';
import { Navigate } from 'react-router-dom';
import { useAuth } from 'oidc-react';

export const RequireAuth = ({children}: { children: JSX.Element }) => {
  const auth = useAuth();

  if (!auth.userData) {
    return <Navigate to="/"/>;
  }

  return children;
};
