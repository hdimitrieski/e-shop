import { CatalogType } from './catalogType';
import { CatalogBrand } from './catalogBrand';

export interface CatalogItem {
  id: number;
  name: string;
  description: string;
  price: number;
  prictureFileName: string;
  catalogType: CatalogType;
  catalogBrand: CatalogBrand;
  availableStock: number;
  restockThreshold: number;
  maxStockThreshold: number;
  onReorder: number;
}
