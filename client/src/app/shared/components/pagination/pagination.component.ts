import { Component, EventEmitter, Input, Output } from '@angular/core';
import { CatalogPage } from '../../../catalog/models';

@Component({
  selector: 'es-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() page: CatalogPage;
  @Output() pageChanged = new EventEmitter<number>();

  public onPageChange(page: number): void {
    if (page - 1 !== this.page.pageable.page) {
      this.pageChanged.emit(page - 1);
    }
  }
}
