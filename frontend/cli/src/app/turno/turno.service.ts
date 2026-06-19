import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
    providedIn: 'root'
})
export class TurnoService {
    private turnosUrl = '/rest/turnos';

    constructor(private http: HttpClient) { }

    
    create(turno: any): Observable<any> {
        return this.http.post<any>(this.turnosUrl, turno);
    }

    getTurnosByPaciente(pacienteId: number, estado?: string, page: number = 0, size: number = 10): Observable<DataPackage> {
        let params = new HttpParams()
            .set('page', page.toString())
            .set('size', size.toString());
        
        if (estado) {
            params = params.set('estado', estado);
        }

        return this.http.get<DataPackage>(`${this.turnosUrl}/paciente/${pacienteId}`, { params });
    }

    reservarTurno(payload: any): Observable<any> {
        return this.http.post<any>(`${this.turnosUrl}/reservar`, payload);
    }


    cancelarReserva(turnoId: number, pacienteId: number): Observable<any> {
        return this.http.patch<any>(`${this.turnosUrl}/id/${turnoId}/cancelar-reserva`, { id: pacienteId });
    }

    confirmarTurno(turnoId: number, paciente: any, forzar: boolean = false): Observable<any> {
        let params = new HttpParams().set('forzar', forzar.toString());
        return this.http.patch<any>(`${this.turnosUrl}/id/${turnoId}/confirmar`, paciente, { params });
    }

    getTurnosParaReprogramar(turnoId: number): Observable<DataPackage> {
        return this.http.get<DataPackage>(`${this.turnosUrl}/${turnoId}/reprogramar`);
    }

    delete(turnoId: number): Observable<any> {
        return this.http.delete<any>(`${this.turnosUrl}/${turnoId}`);
    }
}
