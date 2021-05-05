import { CommonModule } from '@angular/common';
import { HttpClientModule } from '@angular/common/http';
import { NgModule } from '@angular/core';
import { CatalogPageComponent } from './pages/catalog-page.component';
import { CatalogService } from './services/catalog.service';

@NgModule({
  declarations: [
    CatalogPageComponent
  ],
  providers: [
    CatalogService
  ],
  imports: [
    CommonModule, 
    HttpClientModule
  ],
})
export class CatalogModule {}
