import { Component, Input, OnChanges, OnInit, SimpleChanges } from '@angular/core';
import { CatalogItem } from '../models/catalogItem';
import { CatalogService } from '../services/catalog.service';

@Component({
  selector: 'es-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent {
  @Input() catalogItems: CatalogItem[];

}
