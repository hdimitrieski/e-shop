import { Directive, Input } from '@angular/core';

@Directive({
  // tslint:disable-next-line:directive-selector
  selector: 'img[src]',
  // tslint:disable-next-line:no-host-metadata-property
  host: {
    '[src]': 'src',
    '(error)': 'setPlaceholder()'
  }
})
export class ImgDirective {
  private static readonly PLACEHOLDER_IMAGE: string = 'assets/placeholder.png';
  // tslint:disable-next-line:variable-name
  private _src: string = null;

  @Input()
  public set src(value: string) {
    this._src = value ? value : ImgDirective.PLACEHOLDER_IMAGE;
  }

  public get src(): string {
    return this._src;
  }

  public setPlaceholder(): void {
    this.src = ImgDirective.PLACEHOLDER_IMAGE;
  }

}
