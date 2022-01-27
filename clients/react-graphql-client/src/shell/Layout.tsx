import { Container, Row } from 'react-bootstrap';
import React from 'react';
import { Header } from './header';

export const Layout = ({children}: { children: JSX.Element }) => {
  return (
    <Container>
      <Row>
        <Header/>
      </Row>
      <Row>
        {children}
      </Row>
    </Container>
  );
};
