import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { BasketService } from '../basket/services/basket.service';
import { CatalogRoutingModule } from './catalog-routing.module';
import { CatalogComponent } from './components/catalog.component';
import { FilterComponent } from './components/filter.component';
import { ItemComponent } from './components/item.component';
import { PaginationComponent } from './components/pagination.component';
import { CatalogPageComponent } from './pages/catalog-page.component';
import { CatalogService } from './services/catalog.service';

@NgModule({
  declarations: [
    FilterComponent,
    CatalogComponent,
    ItemComponent,
    CatalogPageComponent,
    PaginationComponent
  ],
  providers: [
    CatalogService,
    BasketService
  ],
  imports: [
    CommonModule, 
    HttpClientModule,
    ReactiveFormsModule,
    CatalogRoutingModule
  ]
})
export class CatalogModule {}
