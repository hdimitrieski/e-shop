import {Injectable} from '@angular/core';
import {NgbDateAdapter, NgbDateStruct} from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class DateAdapter extends NgbDateAdapter<number[]> {
  fromModel(value: number[] | null): NgbDateStruct | null {
    return value ? {
      day: value[2],
      month: value[1],
      year: value[0]
    } : null;
  }

  toModel(date: NgbDateStruct | null): number[] | null {
    return date ? [
      date.year,
      date.month,
      date.day
    ] : null;
  }
}
