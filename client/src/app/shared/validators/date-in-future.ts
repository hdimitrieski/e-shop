import { AbstractControl, ValidationErrors } from '@angular/forms';

export const dateInFuture = (control: AbstractControl): ValidationErrors | null => {
  const [year, month, day] = control.value || [];
  const now = new Date(Date.now());
  const validDate = year > now.getFullYear() || month > now.getMonth() + 1 || day > now.getDate();

  if (control.value && !validDate) {
    return {
      date: {
        valid: false,
        invalid: true
      }
    };
  }
  return null;
};
