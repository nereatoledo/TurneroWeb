import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CentroAtencion } from './centro-atencion';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
  providedIn: 'root'
})
export class CentroAtencionService {
  private apiUrl = 'http://localhost:8080/centros';

  constructor(private http: HttpClient) { }

  save(centro: CentroAtencion): Observable<DataPackage> {
    return this.http.post<DataPackage>(this.apiUrl, centro);
  }

  get(id: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.apiUrl}/${id}`);
  }
}