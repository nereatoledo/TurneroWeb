import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CentroAtencion } from './centro-atencion';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CentroAtencionService {
  private apiUrl = 'http://localhost:8080/centros';

  constructor(private http: HttpClient) { }

  crear(centro: CentroAtencion): Observable<any> {
    return this.http.post(this.apiUrl, centro);
  }

  get(id: string): Observable<any> {
    return this.http.get(`${this.apiUrl}/${id}`);
  }
}