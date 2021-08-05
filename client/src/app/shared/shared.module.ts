import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { ImageComponent } from './components/image/image.component';
import { ImgDirective } from './directives/img.directive';
import { PaginationComponent } from './components/pagination/pagination.component';

@NgModule({
  declarations: [
    ImageComponent,
    ImgDirective,
    PaginationComponent
  ],
  providers: [],
  imports: [
    CommonModule,
    NgbModule,
    ReactiveFormsModule
  ],
  exports: [
    NgbModule,
    CommonModule,
    ReactiveFormsModule,
    ImageComponent,
    ImgDirective,
    PaginationComponent
  ]
})
export class SharedModule {

}
