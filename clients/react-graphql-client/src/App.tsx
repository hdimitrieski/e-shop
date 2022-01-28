import React from 'react';
import { CatalogPage, ProductPage } from './catalog';
import { Route, Routes } from 'react-router-dom';
import { BasketPage } from './basket';
import { RequireAuth } from './routes/RequireAuth';
import { OrdersPage } from './orders';
import { CheckoutPage } from './checkout';
import { AppShell } from './shell';

export const App = () => (
  <AppShell>
    <Routes>
      <Route path="/" element={<CatalogPage/>}/>
      <Route path="/product/:productId" element={<ProductPage/>}/>
      <Route path="/basket" element={
        <RequireAuth>
          <BasketPage/>
        </RequireAuth>
      }/>
      <Route path="/orders" element={
        <RequireAuth>
          <OrdersPage/>
        </RequireAuth>
      }/>
      <Route path="/checkout" element={
        <RequireAuth>
          <CheckoutPage/>
        </RequireAuth>
      }/>
    </Routes>
  </AppShell>
);

export default App;
