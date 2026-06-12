import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Medico } from './medico';
import { Observable } from 'rxjs';
import { DataPackage } from '../data-package';

@Injectable({
    providedIn: 'root'
})
export class MedicoService {
    private medicosUrl = "/rest/medicos";

    constructor(private http: HttpClient) { }

    getAll(): Observable<DataPackage> {
        return this.http.get<DataPackage>(this.medicosUrl);
    }

    search(searchTerm: string, especialidadId?: number): Observable<DataPackage> {
        let url = `${this.medicosUrl}/search/${searchTerm}`;
        if (especialidadId !== undefined && especialidadId !== null) {
            url += `?especialidadId=${especialidadId}`;
        }
        return this.http.get<DataPackage>(url);
    }

    remove(id: number): Observable<DataPackage> {
        return this.http.delete<DataPackage>(`${this.medicosUrl}/${id}`);
    }

    save(medico: Medico): Observable<DataPackage> {
        return medico.id ? this.http.put<DataPackage>(this.medicosUrl, medico) :
            this.http.post<DataPackage>(this.medicosUrl, medico);
    }

    get(id: string): Observable<DataPackage> {
        return this.http.get<DataPackage>(`${this.medicosUrl}/id/${id}`);
    }

    byPage(page: number, size: number): Observable<DataPackage> {
        return this.http.get<DataPackage>(`${this.medicosUrl}/page?page=${page - 1}&size=${size}`);
    }
}