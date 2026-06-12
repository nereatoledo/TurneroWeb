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

  search(searchTerm: string, medicoId?: number, especialidadId?: number): Observable<DataPackage> {
    let url = `${this.centrosUrl}/search/${searchTerm}`;
    const params = [];
    if (medicoId !== undefined && medicoId !== null) {
      params.push(`medicoId=${medicoId}`);
    }
    if (especialidadId !== undefined && especialidadId !== null) {
      params.push(`especialidadId=${especialidadId}`);
    }
    if (params.length > 0) {
      url += `?${params.join('&')}`;
    }
    return this.http.get<DataPackage>(url);
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

  obtenerEspecialidadesDeCentro(idCentro: number): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.centrosUrl}/${idCentro}/especialidades`);
  }

  asociarEspecialidad(idCentro: number, idEspecialidad: number): Observable<DataPackage> {
    return this.http.post<DataPackage>(`${this.centrosUrl}/${idCentro}/especialidades/${idEspecialidad}`, {});
  }
  desasociarEspecialidad(idCentro: number, idEspecialidad: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.centrosUrl}/${idCentro}/especialidades/${idEspecialidad}`);
  }

  obtenerMedicosDeCentro(nombreCentro: string): Observable<DataPackage> {
    return this.http.get<DataPackage>(`${this.centrosUrl}/${nombreCentro}/medicos`);
  }

  desasociarMedico(nombreCentro: string, idMedico: number): Observable<DataPackage> {
    return this.http.delete<DataPackage>(`${this.centrosUrl}/desasociar-medico`, {
      body: {
        centro_de_atencion: nombreCentro,
        id_medico: idMedico.toString()
      }
    });
  }
}