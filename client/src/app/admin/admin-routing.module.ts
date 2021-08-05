import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { AdminPageComponent } from './pages/admin-page.component';
import { OrderManagementPageComponent } from './pages/order-management.page.component';
import { CatalogManagementPageComponent } from './pages/catalog-management.page.component';
import { CatalogItemFormPageComponent } from './pages/catalog-item-form-page.component';
import { CatalogItemResolver } from './resolvers/catalog-item.resolver';

const routes: Routes = [
  {
    path: '',
    component: AdminPageComponent,
    children: [
      {
        path: '',
        component: CatalogManagementPageComponent
      },
      {
        path: 'orders',
        component: OrderManagementPageComponent
      },
      {
        path: 'catalog-item',
        component: CatalogItemFormPageComponent,
        resolve: {
          catalogItem: CatalogItemResolver
        }
      },
      {
        path: 'catalog-item/:catalogItemId',
        component: CatalogItemFormPageComponent,
        resolve: {
          catalogItem: CatalogItemResolver
        }
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule {
}
