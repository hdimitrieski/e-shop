import { CatalogType } from './catalog-type';
import { CatalogBrand } from './catalog-brand';
import {Rating} from "./rating";

export interface CatalogItem {
  id: string;
  name: string;
  description: string;
  price: number;
  pictureFileName: string;
  category: CatalogType;
  brand: CatalogBrand;
  availableStock: number;
  rating: Rating;
}
