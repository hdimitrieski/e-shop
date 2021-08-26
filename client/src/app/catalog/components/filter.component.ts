import { Component, OnInit, Output, EventEmitter, Input } from '@angular/core';
import { FormBuilder, FormGroup } from '@angular/forms';
import { CatalogService } from '../../core/services/catalog.service';
import { CatalogBrand, CatalogType, ChangeFilterEvent } from '../models';

@Component({
  selector: 'es-filter',
  templateUrl: './filter.component.html',
  styleUrls: ['./filter.component.css'],
})
export class FilterComponent implements OnInit {
  @Input() types: CatalogType[];
  @Input() brands: CatalogBrand[];

  @Output() filterSubmitted = new EventEmitter<ChangeFilterEvent>();

  filterForm: FormGroup;

  constructor(
    private readonly catalogService: CatalogService,
    private readonly fb: FormBuilder
  ) {}

  ngOnInit(): void {
    this.filterForm = this.fb.group({
      type: this.fb.control('All'),
      brand: this.fb.control('All'),
    });
  }

  onFilterSubmitted(): void {
    this.filterSubmitted.emit(this.filterForm.value);
  }
}
