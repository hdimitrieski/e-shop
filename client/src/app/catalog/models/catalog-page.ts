import { CatalogItem } from './catalog-item';

export interface CatalogPage {
  content: CatalogItem[];
  pageable: any;
  total: number;
  totalPages: number;
  totalElements: number;
}
