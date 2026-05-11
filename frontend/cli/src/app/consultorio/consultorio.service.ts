import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Consultorio } from './consultorio';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
  providedIn: 'root'
})
export class ConsultorioService {
  private consultoriosUrl = "/rest/consultorios";

  constructor(private http: HttpClient) { }

  listByCentro(centroId: string, page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(
      `${this.consultoriosUrl}/centro/${centroId}/consultorio?page=${page - 1}&size=${size}`
    );
  }

  remove(id: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.consultoriosUrl}/${id}`);
  }

  save(centroId: string, consultorio: Consultorio): Observable<DataPackage> {
    return consultorio.id 
      ? this.http.put<DataPackage>(this.consultoriosUrl, consultorio) 
      : this.http.post<DataPackage>(`${this.consultoriosUrl}/centro/${centroId}`, consultorio);
  }

  get(id: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.consultoriosUrl}/id/${id}`);
  }

  byPage(page: number, size: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.consultoriosUrl}/page?page=${page - 1}&size=${size}`);
  }
}