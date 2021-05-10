import { Component, EventEmitter, Input, Output } from "@angular/core";
import { Page } from "../models/page";

@Component({
  selector: 'es-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() page: Page;
  @Output() previousClickEvent = new EventEmitter();
  @Output() nextClickEvent = new EventEmitter();

  previousClicked() {
    this.previousClickEvent.emit();
  }

  nextClicked() {
    this.nextClickEvent.emit();
  }
}