import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
    providedIn: 'root'
})
export class AgendaService {
    private agendaUrl = '/rest/esquemas-turnos';

    constructor(private http: HttpClient) { }

    agendarTurno(turno: any): Observable<any> {
        return this.http.post<any>('/rest/turnos', turno);
    }

    buscarAgenda(fechaInicio: string, fechaFin?: string, idEspecialidad?: number, idMedico?: number, idCentro?: number, idMedicoExcluido?: number, idCentroExcluido?: number): Observable<DataPackage> {
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
        if (idCentro) {
            params = params.set('idCentro', idCentro.toString());
        }
        if (idMedicoExcluido) {
            params = params.set('idMedicoExcluido', idMedicoExcluido.toString());
        }
        if (idCentroExcluido) {
            params = params.set('idCentroExcluido', idCentroExcluido.toString());
        }

        return this.http.get<DataPackage>(`${this.agendaUrl}/buscar`, { params });
    }
}