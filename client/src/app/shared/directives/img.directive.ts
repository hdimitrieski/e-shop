import { Directive, Input } from '@angular/core';

@Directive({
  selector: 'img[src]',
  host: {
    '[src]': 'src',
    '(error)': 'setPlaceholder()'
  }
})
export class ImgDirective {
  private static readonly placeholderImage: string = 'assets/placeholder.png';
  private _src: string = null;

  @Input()
  public set src(value: string) {
    this._src = value ? value : ImgDirective.placeholderImage;
  }

  public get src(): string {
    return this._src;
  }

  public setPlaceholder() {
    this.src = ImgDirective.placeholderImage;
  }

}
