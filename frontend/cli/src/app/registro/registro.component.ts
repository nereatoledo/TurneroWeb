import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from '../login/login.service';

@Component({
    selector: 'app-registro',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule],
    templateUrl: './registro.component.html',
    styleUrls: ['./registro.component.css']
})
export class RegistroComponent implements OnInit {
    paciente = {
        nombre: '',
        apellido: '',
        dni: '',
        fechaNacimiento: '',
        obraSocialId: null as number | null
    };
    username: string = '';
    obrasSociales: any[] = [];
    errorMessage: string = '';
    successMessage: string = '';

    constructor(
        private loginService: LoginService,
        private router: Router
    ) { }

    ngOnInit() {
        this.loginService.getObrasSociales().subscribe({
            next: (data) => this.obrasSociales = data,
            error: (err) => console.error('Error al cargar obras sociales', err)
        });
    }

    onSubmit() {
        this.errorMessage = '';
        this.successMessage = '';

        const payload = {
            nombre: this.paciente.nombre,
            apellido: this.paciente.apellido,
            dni: this.paciente.dni,
            fechaNacimiento: this.paciente.fechaNacimiento,
            obraSocialId: this.paciente.obraSocialId ? Number(this.paciente.obraSocialId) : null,
            username: this.username.trim()
        };

        this.loginService.register(payload).subscribe({
            next: (res) => {
                this.successMessage = '¡Registro completado con éxito! Redirigiendo al login...';
                setTimeout(() => this.router.navigate(['/login']), 2000);
            },
            error: (err) => {
                if (err.error && err.error.error) {
                    this.errorMessage = err.error.error;
                } else {
                    this.errorMessage = 'Ocurrió un error al procesar el registro.';
                }
            }
        });
    }
}