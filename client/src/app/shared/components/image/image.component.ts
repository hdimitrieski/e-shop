import { Component, Input, OnInit } from '@angular/core';
import { ImageSize } from './image-size';

@Component({
  selector: 'es-image',
  styleUrls: ['./image.component.css'],
  template: `
    <img
      [src]="url"
      [alt]="name"
      [ngStyle]="{
        width: width + 'px',
        height: height + 'px'
      }">
  `
})
export class ImageComponent implements OnInit {
  @Input() name: string;
  @Input() size: ImageSize;

  url: string = null;
  width = 200;
  height = 150;

  public ngOnInit() {
    this.setImageSize();
    this.url = `http://localhost:8887/unsafe/${this.width}x${this.height}/${this.name}`;
  }

  private setImageSize() {
    if (!this.size || this.size === ImageSize.Medium) {
      this.width = 240;
      this.height = 150;
    } else if (this.size === ImageSize.Small) {
      this.width = 40;
      this.height = 40;
    }
  }

}
