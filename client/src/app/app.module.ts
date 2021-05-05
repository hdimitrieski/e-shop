import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { CatalogModule } from './catalog/catalog.module';
import { FilterComponent } from './catalog/components/filter.component';
import { CoreModule } from './core/core.module';
import { HeaderComponent } from './header/header.component';

@NgModule({
  declarations: [
    AppComponent, 
    HeaderComponent, 
    FilterComponent
  ],
  imports: [
    BrowserModule, 
    CoreModule, 
    AppRoutingModule, 
    CatalogModule
  ],
  providers: [],
  bootstrap: [
    AppComponent
  ],
})
export class AppModule {}
