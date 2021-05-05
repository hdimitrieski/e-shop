import { CatalogType } from './catalogType';
import { CatalogBrand } from './catalogBrand';

export interface CatalogItem {
  name: string;
  description: string;
  price: number;
  catalogType: CatalogType;
  catalogBrand: CatalogBrand;
  availableStock: number;
}
