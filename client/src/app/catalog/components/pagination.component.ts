import { Component, Input } from "@angular/core";
import { Page } from "../models/page";

@Component({
  selector: 'es-pagination',
  templateUrl: './pagination.component.html',
  styleUrls: ['./pagination.component.css']
})
export class PaginationComponent {
  @Input() page: Page;
  
}