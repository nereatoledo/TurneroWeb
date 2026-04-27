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

    search(searchTerm: string): Observable<DataPackage> {
        return this.http.get<DataPackage>(`${this.especialidadesUrl}/search/${searchTerm}`);
    }

    remove(id: number): Observable<DataPackage> {
        return this.http.delete<DataPackage>(`${this.especialidadesUrl}/${id}`);
    }

    save(centro: Especialidad): Observable<DataPackage> {
        return centro.id ? this.http.put<DataPackage>(this.especialidadesUrl, centro) :
            this.http.post<DataPackage>(this.especialidadesUrl, centro);
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