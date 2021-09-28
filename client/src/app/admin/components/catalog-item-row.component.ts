import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CatalogItem } from '../../catalog/models';
import { Validators } from '@angular/forms';
import { ValueChanged } from '../models/value-changed';

@Component({
  selector: 'es-catalog-item-row',
  templateUrl: './catalog-item-row.component.html',
  styleUrls: ['./catalog-item-row.component.css']
})
export class CatalogItemRowComponent {
  @Input() item: CatalogItem = null;
  @Output() deleted = new EventEmitter<CatalogItem>();
  @Output() nameChanged = new EventEmitter<ValueChanged<string>>();
  @Output() priceChanged = new EventEmitter<ValueChanged<number>>();

  public nameValidators = [
    Validators.required,
    Validators.minLength(5),
    Validators.maxLength(50)
  ];
  public requiredValidator = [
    Validators.required
  ];

  public onNameChanged(name: string) {
    this.nameChanged.emit({
      productId: this.item.id,
      value: name
    });
  }

  public onPriceChanged(price: number) {
    this.priceChanged.emit({
      productId: this.item.id,
      value: price
    });
  }

}
