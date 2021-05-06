import { Component, OnInit } from '@angular/core';
import { CatalogItem } from '../models/catalogItem';
import { CatalogService } from '../services/catalog.service';

@Component({
  selector: 'es-catalog',
  templateUrl: './catalog.component.html',
  styleUrls: ['./catalog.component.css']
})
export class CatalogComponent implements OnInit {
  items: CatalogItem[];

  constructor(private catalogService: CatalogService) {

  }

  ngOnInit() {
    this.catalogService.fetchCatalogItems().subscribe(items => {
      this.items = items.content;
    })
  }

}
