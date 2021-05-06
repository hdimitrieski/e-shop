import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CatalogBrand } from '../models/catalogBrand';
import { CatalogType } from '../models/catalogType';
import { CatalogService } from '../services/catalog.service';

@Component({
  selector: 'es-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit {
  types: CatalogType[];
  brands: CatalogBrand[];

  @Output() typeSubmitted = new EventEmitter();
  @Output() brandSubmitted = new EventEmitter();

  filterForm: FormGroup;

  constructor(
    private catalogService: CatalogService,
    private fb: FormBuilder
  ) {}

  ngOnInit() {
    this.filterForm = this.fb.group({
      type: this.fb.control('All'),
      brand: this.fb.control('All'),
    });

    this.catalogService.fetchCatalogTypes().subscribe((types) => {
      this.types = types;
    });

    this.catalogService.fetchCatalogBrands().subscribe((brands) => {
      this.brands = brands;
    });
  }

  onFilterSubmitted() {
    console.log(this.filterForm.value);
    this.typeSubmitted.emit(this.filterForm.value.type);
    this.brandSubmitted.emit(this.filterForm.value.brand);
  }
}
