import { Component, OnInit } from '@angular/core';
import { CommonModule, Location } from '@angular/common'; 
import { FormsModule } from '@angular/forms';
import { ActivatedRoute, Router, RouterModule } from '@angular/router';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { Observable, of } from 'rxjs';
import { catchError, debounceTime, distinctUntilChanged, map, switchMap } from 'rxjs/operators';

import { AgendaService } from './agenda.service';
import { ModalService } from '../modal/modal.service';
import { LoginService } from '../login/login.service';
import { EspecialidadService } from '../especialidades/especialidad.service';
import { MedicoService } from '../medico/medico.service';
import { CentroAtencionService } from '../centro/centro-atencion.service';
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
    centroSeleccionado: any = null;
    isCentroLocked: boolean = false;

    agendas: AgendaDia[] = [];
    busquedaRealizada: boolean = false;
    mensajeError: string = '';
    sugerenciasMismoMedico: any[] = [];
    sugerenciasOtrosMedicos: any[] = [];
    buscandoSugerencias: boolean = false;

    constructor(
        private agendaService: AgendaService,
        private espService: EspecialidadService,
        private medicoService: MedicoService,
        private centroService: CentroAtencionService,
        private router: Router,
        private route: ActivatedRoute,
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

        this.route.queryParams.subscribe(params => {
            const centroIdParam = params['centroId'];
            if (centroIdParam) {
                const id = Number(centroIdParam);
                if (!isNaN(id)) {
                    this.cargarCentroPreseleccionado(id);
                }
            }
        });
    }

    cargarCentroPreseleccionado(id: number): void {
        this.centroService.byId(id).subscribe({
            next: (res: DataPackage) => {
                this.centroSeleccionado = res.data;
                this.isCentroLocked = true;
                this.buscarAgenda();
            },
            error: (err: any) => {
                console.error('Error al cargar el centro preseleccionado', err);
            }
        });
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

    searchCentro = (text$: Observable<string>): Observable<any[]> =>
        text$.pipe(
            debounceTime(300),
            distinctUntilChanged(),
            switchMap((term) =>
                term.length < 1 ? of([]) :
                this.centroService.search(term).pipe(
                    map((response) => <any[]>response.data),
                    catchError(() => of([]))
                )
            )
        );

    resultFormatCentro(value: any): string { return value.nombre; }
    inputFormatCentro(value: any): string { return value ? value.nombre : ''; }

    onEspecialidadChange(): void {
        if (this.especialidadSeleccionada) {
            if (this.medicoSeleccionado && this.medicoSeleccionado.especialidad.id !== this.especialidadSeleccionada.id) {
                this.medicoSeleccionado = null;
            }
            if (this.centroSeleccionado && this.centroSeleccionado.especialidades) {
                const tieneEsp = this.centroSeleccionado.especialidades.some((e: any) => e.id === this.especialidadSeleccionada.id);
                if (!tieneEsp) {
                    this.centroSeleccionado = null;
                }
            }
        }
    }

    onMedicoChange(): void {
        if (this.medicoSeleccionado) {
            if (!this.especialidadSeleccionada || this.especialidadSeleccionada.id !== this.medicoSeleccionado.especialidad.id) {
                this.especialidadSeleccionada = this.medicoSeleccionado.especialidad;
            }
        }
    }

    onCentroChange(): void {
        if (this.centroSeleccionado && this.medicoSeleccionado) {
            this.medicoSeleccionado = null;
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

        if (this.especialidadSeleccionada && typeof this.especialidadSeleccionada === 'string') {
            this.mensajeError = 'Por favor, seleccione una especialidad de la lista desplegable.';
            return;
        }
        if (this.medicoSeleccionado && typeof this.medicoSeleccionado === 'string') {
            if (this.medicoSeleccionado.toLowerCase() === 'todos') {
                this.medicoSeleccionado = null;
            } else {
                this.mensajeError = 'Por favor, seleccione un médico de la lista desplegable.';
                return;
            }
        }
        if (this.centroSeleccionado && typeof this.centroSeleccionado === 'string') {
            this.mensajeError = 'Por favor, seleccione un centro de atención de la lista desplegable.';
            return;
        }

        this.mensajeError = '';
        this.busquedaRealizada = true;
        this.sugerenciasMismoMedico = [];
        this.sugerenciasOtrosMedicos = [];

        const idEspecialidad = this.especialidadSeleccionada ? this.especialidadSeleccionada.id : undefined;
        const idMedico = this.medicoSeleccionado ? this.medicoSeleccionado.id : undefined;
        const idCentro = this.centroSeleccionado ? this.centroSeleccionado.id : undefined;

        this.agendaService.buscarAgenda(
            this.fechaInicio,
            this.fechaFin ? this.fechaFin : undefined,
            idEspecialidad,
            idMedico,
            idCentro
        ).subscribe({
            next: (res: DataPackage) => {
                this.agendas = res.data as AgendaDia[];
                this.agendas = res.data as AgendaDia[];
                if (idCentro && idEspecialidad && !this.tieneTurnosDisponibles(this.agendas)) {
                    this.buscarAlternativas(this.fechaInicio, this.fechaFin, idEspecialidad, idMedico, idCentro);
                }
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

    obtenerSlotsConsolidados(dia: AgendaDia): any[] {
        const mapaSlots = new Map<string, { horario: string, estaDisponible: boolean, esquemasDisponibles: any[] }>();

        for (const esquema of dia.agendaDetalles) {
            for (const slot of esquema.turnos) {
                const key = slot.horario;
                if (!mapaSlots.has(key)) {
                    mapaSlots.set(key, {
                        horario: slot.horario,
                        estaDisponible: false,
                        esquemasDisponibles: []
                    });
                }
                const entry = mapaSlots.get(key)!;
                if (slot.estaDisponible) {
                    entry.estaDisponible = true;
                    entry.esquemasDisponibles.push(esquema);
                }
            }
        }

        return Array.from(mapaSlots.values()).sort((a, b) => a.horario.localeCompare(b.horario));
    }

    agendarTurnoConsolidado(dia: AgendaDia, slotConsolidado: any): void {
        if (slotConsolidado.esquemasDisponibles.length > 0) {
            const esquemaAsignado = slotConsolidado.esquemasDisponibles[0];
            const slotOriginal = esquemaAsignado.turnos.find((t: any) => t.horario === slotConsolidado.horario);
            this.agendarTurno(dia, esquemaAsignado, slotOriginal);
        }
    }

    obtenerRangoHorarioDia(dia: AgendaDia): string {
        if (!dia.agendaDetalles || dia.agendaDetalles.length === 0) return '';
        let min = dia.agendaDetalles[0].horaInicio;
        let max = dia.agendaDetalles[0].horaFin;
        for (const e of dia.agendaDetalles) {
            if (e.horaInicio < min) min = e.horaInicio;
            if (e.horaFin > max) max = e.horaFin;
        }
        return `${this.formatearHora(min)} a ${this.formatearHora(max)}`;
    }

    tieneTurnosDisponibles(dias: AgendaDia[]): boolean {
        if (!dias || dias.length === 0) return false;
        return dias.some(dia => 
            !dia.esFeriado && 
            dia.agendaDetalles && 
            dia.agendaDetalles.some(esquema => 
                esquema.turnos && esquema.turnos.some((t: any) => t.estaDisponible)
            )
        );
    }

    buscarAlternativas(fechaInicio: string, fechaFin: string, idEspecialidad: number, idMedico: number | undefined, idCentro: number): void {
        this.buscandoSugerencias = true;

        // Alternativa 1: Otros centros (mismo médico si se especificó, cualquier médico de la especialidad si no)
        this.agendaService.buscarAgenda(fechaInicio, fechaFin, idEspecialidad, idMedico, undefined, undefined, idCentro).subscribe({
            next: (res: DataPackage) => {
                const diasSugeridos = res.data as AgendaDia[];
                const listaSugerencias: any[] = [];
                for (const dia of diasSugeridos) {
                    if (dia.esFeriado) continue;
                    for (const esquema of dia.agendaDetalles) {
                        for (const slot of esquema.turnos) {
                            if (slot.estaDisponible) {
                                listaSugerencias.push({
                                    dia: dia,
                                    esquema: esquema,
                                    slot: slot
                                });
                            }
                        }
                    }
                }
                this.sugerenciasMismoMedico = listaSugerencias.sort((a, b) => {
                    const compFecha = a.dia.fecha.localeCompare(b.dia.fecha);
                    if (compFecha !== 0) return compFecha;
                    return a.slot.horario.localeCompare(b.slot.horario);
                });
                if (!idMedico) this.checkBuscandoSugerenciasFinalizado();
            },
            error: () => {
                if (!idMedico) this.checkBuscandoSugerenciasFinalizado();
            }
        });

        // Alternativa 2: Otros médicos de la misma especialidad en el mismo centro
        if (idMedico) {
            this.agendaService.buscarAgenda(fechaInicio, fechaFin, idEspecialidad, undefined, idCentro, idMedico, undefined).subscribe({
                next: (res: DataPackage) => {
                    const diasSugeridos = res.data as AgendaDia[];
                    const listaSugerencias: any[] = [];
                    for (const dia of diasSugeridos) {
                        if (dia.esFeriado) continue;
                        for (const esquema of dia.agendaDetalles) {
                            for (const slot of esquema.turnos) {
                                if (slot.estaDisponible) {
                                    listaSugerencias.push({
                                        dia: dia,
                                        esquema: esquema,
                                        slot: slot
                                    });
                                }
                            }
                        }
                    }
                    this.sugerenciasOtrosMedicos = listaSugerencias.sort((a, b) => {
                        const compFecha = a.dia.fecha.localeCompare(b.dia.fecha);
                        if (compFecha !== 0) return compFecha;
                        return a.slot.horario.localeCompare(b.slot.horario);
                    });
                    this.checkBuscandoSugerenciasFinalizado();
                },
                error: () => this.checkBuscandoSugerenciasFinalizado()
            });
        }
    }

    private checkBuscandoSugerenciasFinalizado(): void {
        this.buscandoSugerencias = false;
    }
}