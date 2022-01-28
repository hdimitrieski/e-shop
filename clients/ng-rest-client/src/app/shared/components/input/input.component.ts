import {
  Component,
  ContentChild,
  ElementRef,
  EventEmitter, HostBinding,
  HostListener,
  Input,
  OnInit,
  Output,
  TemplateRef
} from '@angular/core';
import { FormControl, FormGroup } from '@angular/forms';

@Component({
  selector: 'es-input',
  templateUrl: './input.component.html',
  styleUrls: ['./input.component.css']
})
export class InputComponent implements OnInit {
  @Input() name: string;
  @Input() value: any;
  @Input() validators = [];

  @Output() submitted = new EventEmitter<any>();

  @ContentChild(TemplateRef) inputTemplate: TemplateRef<any>;

  @HostBinding('class.readOnly')
  readOnly = true;

  form: FormGroup;
  inputContext: any = {};

  constructor(private readonly elementRef: ElementRef) {
  }

  ngOnInit(): void {
    this.form = new FormGroup({
      [this.name]: new FormControl(this.value, this.validators)
    });
    this.inputContext = {
      control: this.form.get(this.name)
    };
  }

  onSubmit() {
    this.submitted.emit(this.form.value[this.name]);
    this.readOnly = true;
  }

  onFocusOut() {
    this.onSubmit();
  }

  @HostListener('document:click', ['$event'])
  onClick(event: Event) {
    if (this.readOnly && this.elementRef.nativeElement.contains(event.target)) {
      this.readOnly = false;
    } else if (!this.elementRef.nativeElement.contains(event.target)) {
      this.readOnly = true;
    }
  }

}

