import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Especialidad } from './especialidades';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
    providedIn: 'root'
})
export class EspecialidadService {
    private especialidadesUrl = "/rest/especialidades";

    constructor(private http: HttpClient) { }

    getAll(): Observable<DataPackage> {
        return this.http.get<DataPackage>(this.especialidadesUrl);
    }

    search(searchTerm: string, centroId?: number): Observable<DataPackage> {
        let url = `${this.especialidadesUrl}/search/${searchTerm}`;
        if (centroId !== undefined && centroId !== null) {
            url += `?centroId=${centroId}`;
        }
        return this.http.get<DataPackage>(url);
    }

    remove(id: number): Observable<DataPackage> {
        return this.http.delete<DataPackage>(`${this.especialidadesUrl}/${id}`);
    }

    save(especialidad: Especialidad): Observable<DataPackage> {
        return especialidad.id ? this.http.put<DataPackage>(this.especialidadesUrl, especialidad) :
            this.http.post<DataPackage>(this.especialidadesUrl, especialidad);
    }

    get(id: string): Observable<DataPackage> {
        return this.http.get<DataPackage>(`${this.especialidadesUrl}/id/${id}`);
    }

    byPage(page: number, size: number): Observable<DataPackage> {
        return this.http.get<DataPackage>(`${this.especialidadesUrl}/page?page=${page - 1}&size=${size}`);
    }

    byId(id: number): Observable<DataPackage> {
        return this.http.get<DataPackage>(`${this.especialidadesUrl}/id/${id}`);
    }
}