import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    private authUrl = '/rest/auth';
    constructor(private http: HttpClient) { }
    login(username: string): Observable<any> {
        return this.http.post<any>(`${this.authUrl}/login`, { username });
    }
    getObrasSociales(): Observable<any[]> {
        return this.http.get<any[]>(`${this.authUrl}/obras-sociales`);
    }
    register(paciente: any): Observable<any> {
        return this.http.post<any>(`${this.authUrl}/register`, paciente);
    }
}