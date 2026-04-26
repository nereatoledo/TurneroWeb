import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { CentroAtencion } from './centro-atencion';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
  providedIn: 'root'
})
export class CentroAtencionService {
  private centrosUrl = "/rest/centros";

  constructor(private http: HttpClient) { }

  search(searchTerm: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.centrosUrl}/search/${searchTerm}`);
  }

  remove(id: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.centrosUrl}/${id}`);
  }

  save(centro: CentroAtencion): Observable<DataPackage> {
    return centro.id ? this.http.put<DataPackage>(this.centrosUrl, centro) :
      this.http.post<DataPackage>(this.centrosUrl, centro);
  }

  get(id: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.centrosUrl}/id/${id}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.centrosUrl}/page?page=${page - 1}&size=${size}`);
  }

  byId(id: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.centrosUrl}/id/${id}`);
  }
}