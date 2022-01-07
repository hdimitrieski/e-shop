import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CheckoutForm } from '../models';
import { Observable } from 'rxjs';
import { AuthenticationService } from '../../core/services/authentication.service';
import { map, switchMap } from 'rxjs/operators';
import { v4 as uuid } from 'uuid';
import { environment } from '../../../environments/environment';

@Injectable()
export class CheckoutService {
  constructor(private readonly http: HttpClient, private readonly authService: AuthenticationService) {
  }

  checkout(checkoutForm: CheckoutForm): Observable<void> {
    return this.authService.loadUserProfile().pipe(
      map(user => ({
        ...checkoutForm,
        buyer: user.preferred_username
      })),
      switchMap(checkoutRequest =>
        this.http.post<void>(
          `${environment.apiUrl}/api/v1/basket/checkout`,
          checkoutRequest,
          {
            headers: {
              'x-requestid': uuid()
            }
          }
        )
      )
    );
  }

}
