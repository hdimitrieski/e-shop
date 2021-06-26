import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BasketRoutingModule } from './basket-routing.module';
import { BasketPageComponent } from './pages/basket-page.component';
import { SharedModule } from '../shared/shared.module';

@NgModule({
  declarations: [
    BasketPageComponent
  ],
  providers: [],
  imports: [
    HttpClientModule,
    BasketRoutingModule,
    SharedModule
  ]
})
export class BasketModule {

}
