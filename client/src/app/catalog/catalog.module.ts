import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CatalogRoutingModule } from './catalog-routing.module';
import { CatalogComponent } from './components/catalog.component';
import { FilterComponent } from './components/filter.component';
import { ItemComponent } from './components/item.component';
import { CatalogPageComponent } from './pages/catalog-page.component';
import { SharedModule } from '../shared/shared.module';
import { TopFiveCatalogItemsComponent } from './components/top-five-catalog-items.component';

@NgModule({
  declarations: [
    FilterComponent,
    CatalogComponent,
    ItemComponent,
    CatalogPageComponent,
    TopFiveCatalogItemsComponent
  ],
  providers: [
  ],
  imports: [
    HttpClientModule,
    CatalogRoutingModule,
    SharedModule
  ]
})
export class CatalogModule {
}
