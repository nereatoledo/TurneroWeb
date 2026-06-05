import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common'; 
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap } from 'rxjs/operators';

import { AgendaService } from './agenda.service';
import { EspecialidadService } from '../especialidades/especialidad.service';
import { MedicoService } from '../medico/medico.service';
import { AgendaDia } from './agenda';
import { DataPackage } from '../data-package';

@Component({
    selector: 'app-agenda',
    standalone: true,
    imports: [CommonModule, FormsModule, RouterModule, NgbTypeaheadModule],
    templateUrl: './agenda.component.html',
    styleUrls: ['./agenda.component.css']
})
export class AgendaComponent implements OnInit {

    isAdmin: boolean = false;
    prefix: string = '/usuario';

    fechaInicio: string = '';
    fechaFin: string = '';

    especialidadSeleccionada: any = null;
    medicoSeleccionado: any = null;

    agendas: AgendaDia[] = [];
    busquedaRealizada: boolean = false;
    mensajeError: string = '';

    constructor(
        private agendaService: AgendaService,
        private espService: EspecialidadService,
        private medicoService: MedicoService,
        private router: Router,       
        private location: Location    
    ) { }

    ngOnInit(): void {
        this.isAdmin = this.router.url.includes('/admin');
        this.prefix = this.isAdmin ? '/admin' : '/usuario';

        const hoy = new Date();
        const enUnaSemana = new Date();
        enUnaSemana.setDate(hoy.getDate() + 7);

        this.fechaInicio = hoy.toISOString().split('T')[0];
        this.fechaFin = enUnaSemana.toISOString().split('T')[0];
    }

    searchEspecialidad = (text$: Observable<string>): Observable<any[]> =>
        text$.pipe(
            debounceTime(300),
            distinctUntilChanged(),
            switchMap((term) =>
                term.length < 1 ? of([]) :
                this.espService.search(term).pipe(
                    map((response) => <any[]>response.data),
                    catchError(() => of([]))
                )
            )
        );

    resultFormatEsp(value: any): string { return value.nombre; }
    inputFormatEsp(value: any): string { return value ? value.nombre : ''; }

    searchMedico = (text$: Observable<string>): Observable<any[]> =>
        text$.pipe(
            debounceTime(300),
            distinctUntilChanged(),
            switchMap((term) =>
                term.length < 1 ? of([]) :
                this.medicoService.search(term).pipe(
                    map((response) => <any[]>response.data),
                    catchError(() => of([]))
                )
            )
        );

    resultFormatMed(value: any): string { return value.apellido + ', ' + value.nombre; }
    inputFormatMed(value: any): string { return value ? value.apellido + ', ' + value.nombre : ''; }

    goBack(): void {
        this.location.back();
    }

    buscarAgenda(): void {
        if (!this.fechaInicio) {
            this.mensajeError = 'Por favor, seleccione un rango de fechas válido.';
            return;
        }

        this.mensajeError = '';
        this.busquedaRealizada = true;

        const idEspecialidad = this.especialidadSeleccionada ? this.especialidadSeleccionada.id : undefined;
        const idMedico = this.medicoSeleccionado ? this.medicoSeleccionado.id : undefined;

        this.agendaService.buscarAgenda(
            this.fechaInicio,
            this.fechaFin ? this.fechaFin : undefined,
            idEspecialidad,
            idMedico
        ).subscribe({
            next: (res: DataPackage) => {
                this.agendas = res.data as AgendaDia[];
            },
            error: (err: any) => {
                console.error(err);
                this.mensajeError = 'Error al obtener la agenda. Intente nuevamente.';
            }
        });
    }

    formatearHora(horaStr: string): string {
        return horaStr ? horaStr.substring(0, 5) : '';
    }
}   