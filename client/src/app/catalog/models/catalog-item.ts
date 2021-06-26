import { CatalogType } from './catalog-type';
import { CatalogBrand } from './catalog-brand';

export interface CatalogItem {
  id: number;
  name: string;
  description: string;
  price: number;
  pictureFileName: string;
  catalogType: CatalogType;
  catalogBrand: CatalogBrand;
  availableStock: number;
  restockThreshold: number;
  maxStockThreshold: number;
  onReorder: number;
}
