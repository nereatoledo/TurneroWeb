import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common'; 
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap } from 'rxjs/operators';

import { AgendaService } from './agenda.service';
import { ModalService } from '../modal/modal.service';
import { LoginService } from '../login/login.service';
import { EspecialidadService } from '../especialidades/especialidad.service';
import { MedicoService } from '../medico/medico.service';
import { AgendaDia, EsquemaTurnoAgenda, TurnoSlot } from './agenda';
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
        private modalService: ModalService,
        private loginService: LoginService,       
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
                this.medicoService.search(term, this.especialidadSeleccionada ? this.especialidadSeleccionada.id : undefined).pipe(
                    map((response) => <any[]>response.data),
                    catchError(() => of([]))
                )
            )
        );

    resultFormatMed(value: any): string { return value.apellido + ', ' + value.nombre; }
    inputFormatMed(value: any): string { return value ? value.apellido + ', ' + value.nombre : ''; }

    onEspecialidadChange(): void {
        if (this.especialidadSeleccionada && this.medicoSeleccionado) {
            if (this.medicoSeleccionado.especialidad.id !== this.especialidadSeleccionada.id) {
                this.medicoSeleccionado = null;
            }
        }
    }

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

    
    agendarTurno(dia: AgendaDia, esquema: EsquemaTurnoAgenda, slot: TurnoSlot): void {
        const currentUser = this.loginService.getCurrentUser();
        if (!currentUser || !currentUser.id) {
            this.modalService.info('Error', 'Debe iniciar sesión para agendar un turno.');
            return;
        }

        const horaInicio = slot.horario;
        const horaFin = this.calcularHoraFin(horaInicio, esquema.medico.especialidad.intervalo);
        
        const fechaFormateada = dia.fecha.split('-').reverse().join('/');

        const detallesTurno = [
            { icon: 'fa fa-calendar', label: 'Fecha', value: fechaFormateada },
            { icon: 'fa fa-clock-o', label: 'Horario', value: this.formatearHora(slot.horario) },
            { icon: 'fa fa-user-md', label: 'Médico', value: `${esquema.medico.apellido}, ${esquema.medico.nombre}` },
            { icon: 'fa fa-stethoscope', label: 'Especialidad', value: esquema.medico.especialidad.nombre },
            { icon: 'fa fa-building', label: 'Centro de atención', value: esquema.centroAtencion.nombre },
            { icon: 'fa fa-map-marker', label: 'Dirección', value: esquema.centroAtencion.direccion }
        ];

        this.modalService.confirm('Confirmar Turno', '¿Querés agendar este turno?', '', detallesTurno)
            .then(() => {
                const turnoPayload = {
                    fecha: dia.fecha,
                    horaInicio: horaInicio,
                    horaFin: horaFin,
                    estado: 'PROGRAMADO',
                    paciente: { id: currentUser.id },
                    medico: { id: esquema.medico.id },
                    consultorio: { id: esquema.consultorio.id }
                };

                this.agendaService.agendarTurno(turnoPayload).subscribe({
                    next: () => {
                        this.modalService.info('Éxito', 'Turno Agendado exitosamente')
                            .then(() => { this.buscarAgenda(); })
                            .catch(() => { this.buscarAgenda(); });
                    },
                    error: (err: any) => {
                        console.error(err);
                        
                        const mensajeModal = err.error?.error || 'No se pudo agendar el turno. Intente nuevamente.';

                        this.modalService.info('Error', mensajeModal)
                            .then(() => { this.buscarAgenda(); })
                            .catch(() => { this.buscarAgenda(); });
                    }
                });
            })
            .catch(() => {
            });
    }

    private calcularHoraFin(horaInicio: string, intervaloMinutos?: number): string {
        if (!intervaloMinutos) intervaloMinutos = 30; // default
        const [horas, minutos, segundos] = horaInicio.split(':').map(Number);
        const date = new Date();
        date.setHours(horas, minutos, segundos || 0);
        date.setMinutes(date.getMinutes() + intervaloMinutos);
        const formatZero = (num: number) => num < 10 ? '0' + num : num;
        return formatZero(date.getHours()) + ':' + formatZero(date.getMinutes()) + ':00';
    }

    formatearHora(horaStr: string): string {
        return horaStr ? horaStr.substring(0, 5) : '';
    }
}