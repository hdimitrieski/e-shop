import { CatalogBrand } from './catalog-brand';
import { CatalogType } from './catalog-type';

export interface ChangeFilterEvent {
  brand: CatalogBrand;
  type: CatalogType;
}
