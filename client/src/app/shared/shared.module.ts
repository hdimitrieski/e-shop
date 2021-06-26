import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';
import { NgbModule } from '@ng-bootstrap/ng-bootstrap';
import { ReactiveFormsModule } from '@angular/forms';
import { ImageComponent } from './components/image/image.component';
import { ImgDirective } from './directives/img.directive';

@NgModule({
  declarations: [
    ImageComponent,
    ImgDirective
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
    ImgDirective
  ]
})
export class SharedModule {

}
