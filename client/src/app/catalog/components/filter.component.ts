import { Component, OnInit } from '@angular/core';
import { CatalogBrand } from '../models/catalogBrand';
import { CatalogType } from '../models/catalogType';
import { CatalogService } from '../services/catalog.service';

@Component({
  selector: 'es-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit {
  types: any;
  brands: CatalogBrand[];

  constructor(private catalogService: CatalogService) {}

  ngOnInit() {
    console.log('AA');
    this.catalogService.fetchCatalogTypes().subscribe((data) => {
      console.log('data', data);
      this.types = data;
    });
  }
}
