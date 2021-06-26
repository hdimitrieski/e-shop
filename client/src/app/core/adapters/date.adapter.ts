import { Injectable } from '@angular/core';
import { NgbDateAdapter, NgbDateStruct } from '@ng-bootstrap/ng-bootstrap';

@Injectable()
export class DateAdapter extends NgbDateAdapter<number[]> {
  fromModel(value: number[] | null): NgbDateStruct | null {
    if (!value) return null;
    return {
      day: value[2],
      month: value[1],
      year: value[0]
    };
  }

  toModel(date: NgbDateStruct | null): number[] | null {
    if (!date) return null;
    return [
      date.year,
      date.month,
      date.day
    ];
  }
}
