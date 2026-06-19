import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule, ActivatedRoute } from '@angular/router';
import { LoginService } from '../home/login.service';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable } from 'rxjs';
import { debounceTime, distinctUntilChanged, map } from 'rxjs/operators';

@Component({
    selector: 'app-registro',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule, NgbTypeaheadModule],
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
    isEditMode: boolean = false;
    pacienteId: number | null = null;
    obraSocialSeleccionada: any = '';

    constructor(
        private loginService: LoginService,
        private router: Router,
        private route: ActivatedRoute
    ) { }

    ngOnInit() {
        this.loginService.getObrasSociales().subscribe({
            next: (data) => this.obrasSociales = data,
            error: (err) => console.error('Error al cargar obras sociales', err)
        });

        this.route.paramMap.subscribe(params => {
            const id = params.get('id');
            if (id) {
                this.isEditMode = true;
                this.pacienteId = +id;
                this.cargarPaciente(this.pacienteId);
            }
        });
    }

    cargarPaciente(id: number) {
        this.loginService.getPaciente(id).subscribe({
            next: (data) => {
                this.paciente.nombre = data.nombre;
                this.paciente.apellido = data.apellido;
                this.paciente.dni = data.dni;
                this.paciente.fechaNacimiento = data.fechaNacimiento;
                this.paciente.obraSocialId = data.obraSocial ? data.obraSocial.id : null;
                this.obraSocialSeleccionada = data.obraSocial ? data.obraSocial.nombre : 'Particular / Ninguna';
                this.username = data.username;
            },
            error: (err) => {
                this.errorMessage = 'No se pudo cargar el perfil del paciente.';
            }
        });
    }

    searchObraSocial = (text$: Observable<string>) =>
        text$.pipe(
            debounceTime(200),
            distinctUntilChanged(),
            map(term => term.length < 1 ? []
                : this.obrasSociales.filter(v => v.nombre.toLowerCase().indexOf(term.toLowerCase()) > -1).slice(0, 10))
        );

    resultFormatOS = (value: any) => value.nombre;
    inputFormatOS = (value: any) => value ? (value.nombre || value) : '';

    onObraSocialChange() {
        let nombre = typeof this.obraSocialSeleccionada === 'string' 
            ? this.obraSocialSeleccionada 
            : this.obraSocialSeleccionada?.nombre;

        if (!nombre || nombre === 'Particular / Ninguna') {
            this.paciente.obraSocialId = null;
            return;
        }
        const found = this.obrasSociales.find(os => os.nombre === nombre);
        if (found) {
            this.paciente.obraSocialId = found.id;
        } else {
            this.paciente.obraSocialId = null;
        }
    }

    onSubmit() {
        this.errorMessage = '';
        this.successMessage = '';

        if (this.isEditMode && this.pacienteId) {
            const payload = {
                obraSocialId: this.paciente.obraSocialId ? Number(this.paciente.obraSocialId) : null,
                username: this.username.trim()
            };

            this.loginService.updatePaciente(this.pacienteId, payload).subscribe({
                next: (res) => {
                    this.successMessage = '¡Perfil actualizado con éxito!';
                    setTimeout(() => this.router.navigate(['/usuario']), 2000);
                },
                error: (err) => {
                    if (err.error && err.error.error) {
                        this.errorMessage = err.error.error;
                    } else {
                        this.errorMessage = 'Ocurrió un error al actualizar el perfil.';
                    }
                }
            });
        } else {
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
}