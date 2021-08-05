import { HttpClient } from '@angular/common/http';
import { environment } from '../../../environments/environment';
import { Injectable } from '@angular/core';

@Injectable({providedIn: 'root'})
export class UploadImageService {
  constructor(private readonly http: HttpClient) {
  }

  upload(file: File) {
    return this.http.post(`${environment.imageApiUrl}/images/upload?fileName=${file.name}`, file);
  }

}
