import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CentroAtencion } from './centro-atencion';
import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class CentroAtencionService {
  // La URL de tu servidor Java
  private apiUrl = 'http://localhost:8080/centros';

  constructor(private http: HttpClient) { }

  crear(centro: CentroAtencion): Observable<any> {
    return this.http.post(this.apiUrl, centro);
  }
}