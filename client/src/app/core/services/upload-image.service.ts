import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';

interface ImageUploadResponse {
  fileName: string;
}

@Injectable({providedIn: 'root'})
export class UploadImageService {
  constructor(private readonly http: HttpClient) {
  }

  upload(file: File): Observable<ImageUploadResponse> {
    return this.http.post<ImageUploadResponse>(`${environment.imageApiUrl}/images/upload?fileName=${file.name}`, file);
  }

}
