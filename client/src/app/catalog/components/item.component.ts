import { Component, Input } from "@angular/core";
import { CatalogItem } from "../models/catalogItem";

@Component({
  selector: 'es-item',
  templateUrl: './item.component.html',
  styleUrls: ['./item.component.css']
})
export class ItemComponent {
  @Input() item: CatalogItem;
  
}