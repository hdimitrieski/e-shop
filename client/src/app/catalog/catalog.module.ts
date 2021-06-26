import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CatalogRoutingModule } from './catalog-routing.module';
import { CatalogComponent } from './components/catalog.component';
import { FilterComponent } from './components/filter.component';
import { ItemComponent } from './components/item.component';
import { PaginationComponent } from './components/pagination.component';
import { CatalogPageComponent } from './pages/catalog-page.component';
import { CatalogService } from './services/catalog.service';
import { SharedModule } from '../shared/shared.module';
import { TopFiveCatalogItemsComponent } from './components/top-five-catalog-items.component';

@NgModule({
  declarations: [
    FilterComponent,
    CatalogComponent,
    ItemComponent,
    CatalogPageComponent,
    PaginationComponent,
    TopFiveCatalogItemsComponent
  ],
  providers: [
    CatalogService
  ],
  imports: [
    HttpClientModule,
    CatalogRoutingModule,
    SharedModule
  ]
})
export class CatalogModule {
}
