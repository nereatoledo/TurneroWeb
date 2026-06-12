import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable } from 'rxjs';
import { tap } from 'rxjs/operators';

@Injectable({
    providedIn: 'root'
})
export class LoginService {
    private authUrl = '/rest/auth';
    
    private currentUserSubject = new BehaviorSubject<any>(this.getUserFromStorage());
    public currentUser$ = this.currentUserSubject.asObservable();

    constructor(private http: HttpClient) { }

    private getUserFromStorage(): any {
        const username = localStorage.getItem('currentUser');
        const id = localStorage.getItem('currentUserId');
        const rol = localStorage.getItem('currentUserRol');
        if (username) {
            return { username, id: id ? parseInt(id, 10) : null, rol };
        }
        return null;
    }

    login(username: string): Observable<any> {
        return this.http.post<any>(`${this.authUrl}/login`, { username }).pipe(
            tap(res => {
                localStorage.setItem('currentUser', res.username);
                if (res.id) localStorage.setItem('currentUserId', res.id.toString());
                if (res.rol) localStorage.setItem('currentUserRol', res.rol);
                
                this.currentUserSubject.next({
                    username: res.username,
                    id: res.id,
                    rol: res.rol
                });
            })
        );
    }

    logout() {
        localStorage.removeItem('currentUser');
        localStorage.removeItem('currentUserId');
        localStorage.removeItem('currentUserRol');
        this.currentUserSubject.next(null);
    }

    getCurrentUser(): any {
        return this.currentUserSubject.value;
    }

    getObrasSociales(): Observable<any[]> {
        return this.http.get<any[]>(`${this.authUrl}/obras-sociales`);
    }

    register(paciente: any): Observable<any> {
        return this.http.post<any>(`${this.authUrl}/register`, paciente);
    }

    getPaciente(id: number): Observable<any> {
        return this.http.get<any>(`${this.authUrl}/paciente/${id}`);
    }

    updatePaciente(id: number, paciente: any): Observable<any> {
        return this.http.put<any>(`${this.authUrl}/paciente/${id}`, paciente).pipe(
            tap(res => {
                const currentUser = this.getCurrentUser();
                if (currentUser && currentUser.id === id) {
                    localStorage.setItem('currentUser', res.username);
                    this.currentUserSubject.next({
                        ...currentUser,
                        username: res.username
                    });
                }
            })
        );
    }
}