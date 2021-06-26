import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { OrderDraft } from '../../models';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { CheckoutForm } from '../models';

@Component({
  selector: 'es-order-draft',
  templateUrl: './order-draft.component.html',
  styleUrls: ['./order-draft.component.css']
})
export class OrderDraftComponent implements OnInit {
  @Input() orderDraft: OrderDraft;
  @Output() checkout = new EventEmitter<CheckoutForm>();

  checkoutForm: FormGroup = null;

  constructor(private readonly fb: FormBuilder) {
  }

  ngOnInit() {
    this.checkoutForm = this.createForm();
  }

  onCheckout() {
    this.checkout.emit(this.checkoutForm.value);
  }

  private createForm(): FormGroup {
    return this.fb.group({
      city: this.fb.control('', Validators.required),
      street: this.fb.control('', Validators.required),
      state: this.fb.control('', Validators.required),
      country: this.fb.control('', Validators.required),
      zipCode: this.fb.control('', Validators.required),
      cardNumber: this.fb.control('', Validators.required),
      cardHolderName: this.fb.control('', Validators.required),
      cardExpiration: this.fb.control(null, Validators.required),
      cardSecurityNumber: this.fb.control('', [
        Validators.required,
        Validators.minLength(3),
        Validators.maxLength(3)
      ]),
      cardType: this.fb.control('Visa')
    });
  }

}
