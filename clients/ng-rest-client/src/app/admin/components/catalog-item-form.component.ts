import { Component, EventEmitter, Input, OnInit, Output } from '@angular/core';
import { FormBuilder, FormControl, FormGroup, Validators } from '@angular/forms';
import { CatalogBrand, CatalogItem, CatalogType } from '../../catalog/models';
import { UploadImageService } from '../../core/services/upload-image.service';

@Component({
  selector: 'es-catalog-item-form',
  templateUrl: './catalog-item-form.component.html',
  styleUrls: ['./catalog-item-form.component.css']
})
export class CatalogItemFormComponent implements OnInit {
  @Input() catalogItem: any;
  @Input() categories = [];
  @Input() brands = [];

  @Output() submitted = new EventEmitter<CatalogItem>();

  public form: FormGroup = null;

  public get pictureFileNameControl(): FormControl {
    return this.form.controls.pictureFileName as FormControl;
  }

  constructor(private readonly fb: FormBuilder, private readonly uploadImageService: UploadImageService) {
  }

  ngOnInit(): void {
    this.form = this.createForm();
  }

  public onFileSelected(event: Event): void {
    event.preventDefault();
    const target = event.target as HTMLInputElement;
    const file: File = target.files[0];

    if (file) {
      this.pictureFileNameControl.setValue(file.name);
      this.uploadImageService.upload(file).subscribe(console.log);
    }
  }

  public onSubmit(): void {
    const catalogItem: CatalogItem = {
      id: this.catalogItem?.id,
      ...this.form.value,
      brandId: this.form.value.brand.id,
      categoryId: this.form.value.category.id
    };

    this.submitted.emit(catalogItem);
  }

  public compareBrands(c1: CatalogBrand, c2: CatalogBrand): boolean {
    return c1?.id === c2?.id;
  }

  public compareCategories(c1: CatalogType, c2: CatalogType): boolean {
    return c1?.id === c2?.id;
  }

  private createForm(): FormGroup {
    const form = this.fb.group({
      pictureFileName: this.fb.control(''),
      name: this.fb.control('', [
        Validators.required,
        Validators.minLength(5),
        Validators.maxLength(50)
      ]),
      description: this.fb.control('', Validators.required),
      price: this.fb.control('', Validators.required),
      category: this.fb.control('', Validators.required),
      brand: this.fb.control('', Validators.required),
      availableStock: this.fb.control('', Validators.required)
    });

    form.patchValue(this.catalogItem);

    return form;
  }

}


