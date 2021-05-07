import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { ReactiveFormsModule } from '@angular/forms';
import { CatalogComponent } from './components/catalog.component';
import { FilterComponent } from './components/filter.component';
import { ItemComponent } from './components/item.component';
import { CatalogPageComponent } from './pages/catalog-page.component';
import { CatalogService } from './services/catalog.service';

@NgModule({
  declarations: [
    FilterComponent,
    CatalogComponent,
    ItemComponent,
    CatalogPageComponent
  ],
  providers: [
    CatalogService
  ],
  imports: [
    CommonModule, 
    HttpClientModule,
    ReactiveFormsModule
  ],
  exports: [
    CatalogPageComponent
  ]
})
export class CatalogModule {}
