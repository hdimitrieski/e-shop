import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { OrderRoutingModule } from './order-routing.module';
import { OrderDraftComponent } from './components/order-draft.component';
import { OrderPageComponent } from './pages/order-page.component';
import { OrderDraftPageComponent } from './pages/order-draft-page.component';
import { OrdersPageComponent } from './pages/orders-page.component';
import { OrderDraftResolver } from './resolvers/order-draft.resolver';
import { CheckoutService } from './services/checkout.service';
import { OrdersService } from './services/orders.service';
import { OrdersResolver } from './resolvers/orders.resolver';
import { OrderResolver } from './resolvers/order.resolver';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    OrderDraftComponent,
    OrderPageComponent,
    OrdersPageComponent,
    OrderDraftPageComponent
  ],
  providers: [
    CheckoutService,
    OrderDraftResolver,
    OrdersService,
    OrdersResolver,
    OrderResolver
  ],
  imports: [
    HttpClientModule,
    SharedModule,
    OrderRoutingModule
  ]
})
export class OrderModule {

}
