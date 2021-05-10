import { CatalogBrand } from "./catalogBrand";
import { CatalogType } from "./catalogType";

export interface ChangeFilterEvent {
  brand: CatalogBrand;
  type: CatalogType;
}