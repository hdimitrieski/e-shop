import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { BasketRoutingModule } from './basket-routing.module';
import { BasketComponent } from './components/basket.component';

@NgModule({
  declarations: [BasketComponent],
  providers: [],
  imports: [
    CommonModule, 
    HttpClientModule, 
    BasketRoutingModule
  ]
})
export class BasketModule {

}
