import { Directive, ElementRef, Inject, OnDestroy, OnInit, Renderer2, Self } from '@angular/core';
import { NgControl } from '@angular/forms';
import { Subscription } from 'rxjs';

@Directive({
  // tslint:disable-next-line:directive-selector @angular-eslint/directive-selector
  selector: '[formControl],[formControlName]'
})
export class InputErrorsDirective implements OnInit, OnDestroy {
  private subscription = new Subscription();

  constructor(
    private readonly elementRef: ElementRef,
    private readonly renderer: Renderer2,
    @Inject(NgControl) @Self() private readonly ngControl: NgControl
  ) {
  }

  ngOnInit(): void {
    this.subscription.add(this.ngControl.statusChanges.subscribe((status) => {
      if (status === 'VALID') {
        this.renderer.removeClass(this.elementRef.nativeElement, 'is-invalid');
        this.renderer.addClass(this.elementRef.nativeElement, 'is-valid');
      }
      if (status === 'INVALID') {
        this.renderer.removeClass(this.elementRef.nativeElement, 'is-valid');
        this.renderer.addClass(this.elementRef.nativeElement, 'is-invalid');
      }
    }));
  }

  ngOnDestroy(): void {
    this.subscription.unsubscribe();
  }

}
