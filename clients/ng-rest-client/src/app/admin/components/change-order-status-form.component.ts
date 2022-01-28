import { Component, EventEmitter, OnInit, Output } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { OrderStatusForm } from '../models';

@Component({
  selector: 'es-change-order-status-form',
  templateUrl: './change-order-status-form.component.html',
  styleUrls: ['./change-order-status-form.component.css']
})
export class ChangeOrderStatusFormComponent implements OnInit {
  @Output() cancel = new EventEmitter<OrderStatusForm>();
  @Output() ship = new EventEmitter<OrderStatusForm>();

  public form: FormGroup = null;

  constructor(private readonly fb: FormBuilder) {
  }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  public onCancel(): void {
    this.cancel.emit(this.form.value);
  }

  public onShip(): void {
    this.ship.emit(this.form.value);
  }

  private createForm(): FormGroup {
    return this.fb.group({
      orderNumber: this.fb.control('', Validators.required)
    });
  }

}

