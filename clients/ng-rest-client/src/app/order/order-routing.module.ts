import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { OrderPageComponent } from './pages/order-page.component';
import { OrderDraftPageComponent } from './pages/order-draft-page.component';
import { OrderDraftResolver } from './resolvers/order-draft.resolver';
import { OrdersPageComponent } from './pages/orders-page.component';
import { AuthGuard } from '../core/guards/auth.guard';
import { OrdersResolver } from './resolvers/orders.resolver';
import { OrderResolver } from './resolvers/order.resolver';

const routes: Routes = [
  {
    path: 'orders',
    component: OrdersPageComponent,
    canActivate: [
      AuthGuard
    ],
    resolve: {
      orders: OrdersResolver
    }
  },
  {
    path: 'orders/:orderId',
    component: OrderPageComponent,
    resolve: {
      order: OrderResolver
    }
  },
  {
    path: 'order-draft',
    component: OrderDraftPageComponent,
    canActivate: [
      AuthGuard
    ],
    resolve: {
      orderDraft: OrderDraftResolver
    }
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OrderRoutingModule {

}
