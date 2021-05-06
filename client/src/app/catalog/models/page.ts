import { CatalogItem } from "./catalogItem";

export interface Page {
  content: CatalogItem[];
  pageable: any;
  total: number;
  totalPages: number;
  totalElements: number;
}