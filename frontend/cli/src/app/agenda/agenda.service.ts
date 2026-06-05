import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
    providedIn: 'root'
})
export class AgendaService {
    private agendaUrl = 'http://localhost:8080/esquemas-turnos';

    constructor(private http: HttpClient) { }

    buscarAgenda(fechaInicio: string, fechaFin?: string, idEspecialidad?: number, idMedico?: number): Observable<DataPackage> {
        let params = new HttpParams().set('fechaInicio', fechaInicio);

        if (fechaFin) {
            params = params.set('fechaFin', fechaFin);
        }
        if (idEspecialidad) {
            params = params.set('idEspecialidad', idEspecialidad.toString());
        }
        if (idMedico) {
            params = params.set('idMedico', idMedico.toString());
        }

        return this.http.get<DataPackage>(`${this.agendaUrl}/buscar`, { params });
    }
}