import { Component, OnInit, Output, EventEmitter } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CatalogBrand } from '../models/catalogBrand';
import { CatalogType } from '../models/catalogType';
import { ChangeFilterEvent } from '../models/changeFilterEvent';
import { CatalogService } from '../services/catalog.service';

@Component({
  selector: 'es-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit {
  types: CatalogType[];
  brands: CatalogBrand[];

  @Output() filterSubmitted = new EventEmitter<ChangeFilterEvent>();

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
    this.filterSubmitted.emit(this.filterForm.value);
  }
}
