import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { SharedModule } from '../shared/shared.module';
import { AdminRoutingModule } from './admin-routing.module';
import { AdminPageComponent } from './pages/admin-page.component';
import { OrderManagementPageComponent } from './pages/order-management.page.component';
import { CatalogManagementPageComponent } from './pages/catalog-management.page.component';
import { ChangeOrderStatusFormComponent } from './components/change-order-status-form.component';
import { OrderManagementService } from './services/order-management.service';
import { CatalogItemFormComponent } from './components/catalog-item-form.component';
import { CatalogItemFormPageComponent } from './pages/catalog-item-form-page.component';
import { CatalogItemResolver } from './resolvers/catalog-item.resolver';

@NgModule({
  declarations: [
    AdminPageComponent,
    OrderManagementPageComponent,
    CatalogManagementPageComponent,
    CatalogItemFormPageComponent,
    ChangeOrderStatusFormComponent,
    CatalogItemFormComponent
  ],
  providers: [
    OrderManagementService,
    CatalogItemResolver
  ],
  imports: [
    HttpClientModule,
    SharedModule,
    AdminRoutingModule
  ]
})
export class AdminModule {

}

