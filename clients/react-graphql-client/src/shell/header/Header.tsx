import { Container, Nav, Navbar } from 'react-bootstrap';
import { Link } from 'react-router-dom';
import { useAuth } from 'oidc-react';
import { UserBasket } from './UserBasket';
import { useLazyQuery } from '@apollo/client';
import { loader } from 'graphql.macro';
import { Me } from './models';
import { useEffect } from 'react';
import { LoginButton } from './LoginButton';

const userBasketQuery = loader('./UserBasketQuery.graphql');

interface BasketResponse {
  me: Me;
}

export const Header = () => {
  const [getUserBasket, {error, data}] = useLazyQuery<BasketResponse>(userBasketQuery);
  const auth = useAuth();
  const loggedIn = !!auth.userData;

  useEffect(() => {
    if (loggedIn) {
      getUserBasket();
    }
  }, [loggedIn, getUserBasket]);

  const isAdmin = () => {
    return loggedIn && auth.userData?.profile.roles
      .some((role: string) => role === "ROLE_admin");
  };

  return (
    <Navbar collapseOnSelect expand="lg" bg="light" variant="light">
      <Container>
        <Navbar.Brand href="/">E-Shop</Navbar.Brand>
        <Navbar.Toggle aria-controls="responsive-navbar-nav"/>
        <Navbar.Collapse>
          <Nav className="me-auto">
            <Link className="nav-link" to="/">Catalog</Link>
            <Link className="nav-link" to="/basket">Basket</Link>
            <Link className="nav-link" to="/orders">Orders</Link>
            {isAdmin() && <Link className="nav-link" to="/admin">Admin</Link>}
          </Nav>
          <Nav>
            {loggedIn && data?.me && !error && <UserBasket basket={data.me.basket}/>}
            <div className="border-start ps-2">
              <LoginButton/>
            </div>
          </Nav>
        </Navbar.Collapse>
      </Container>
    </Navbar>
  );
};
