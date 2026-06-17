import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { TurnoService } from '../turno/turno.service';
import { LoginService } from '../home/login.service';
import { Router, RouterModule } from '@angular/router';
import { ModalService } from '../modal/modal.service';

@Component({
  selector: 'app-mis-turnos',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './mis-turnos.component.html',
  styleUrls: ['./mis-turnos.component.css']
})
export class MisTurnosComponent implements OnInit {
  turnos: any[] = [];
  page: number = 0;
  size: number = 10;
  totalElements: number = 0;
  totalPages: number = 0;
  estadoFiltro: string = '';

  pacienteId: number | null = null;
  
  errorMessage: string = '';
  successMessage: string = '';

  
  reprogramacionActiva: boolean = false;
  opcionesReprogramar: any[] = [];
  turnoAReprogramar: any = null;
  turnoSeleccionadoParaReprogramar: any = null;

  constructor(
    private turnoService: TurnoService,
    private loginService: LoginService,
    private router: Router,
    private modalService: ModalService
  ) {}

  ngOnInit(): void {
    this.loginService.currentUser$.subscribe(user => {
      if (user && !this.loginService.isAdmin()) {
        this.pacienteId = user.id;
        this.cargarTurnos();
      } else {
        this.router.navigate(['/login']);
      }
    });
  }

  cargarTurnos(pageIndex: number = 0) {
    if (!this.pacienteId) return;
    this.page = pageIndex;
    this.turnoService.getTurnosByPaciente(this.pacienteId, this.estadoFiltro, this.page, this.size).subscribe({
      next: (res: any) => {
        const pageData = res.data;
        this.turnos = pageData.content || [];
        this.totalElements = pageData.totalElements || 0;
        this.totalPages = pageData.totalPages || 0;
      },
      error: (err) => console.error(err)
    });
  }

  isTurnoPasado(turno: any): boolean {
    if (!turno.fecha || !turno.horaInicio) return false;
    const ahora = new Date();
    
    
    const fechaHoraTurno = new Date(`${turno.fecha}T${turno.horaInicio}`);
    return fechaHoraTurno < ahora;
  }

  onFiltroChange() {
    this.cargarTurnos(0);
  }

  confirmar(turno: any) {
    this.errorMessage = '';
    this.successMessage = '';
    this.turnoService.confirmarTurno(turno.id, { id: this.pacienteId }).subscribe({
      next: (res) => {
        if (res.requiereConfirmacion) {
          this.modalService.confirm('Advertencia', res.advertencia, '¿Querés confirmar de todas formas?')
            .then(() => {
              this.forzarConfirmacion(turno.id);
            })
            .catch(() => {});
        } else {
          this.successMessage = 'Turno confirmado correctamente.';
          this.cargarTurnos(this.page);
        }
      },
      error: (err) => this.errorMessage = err.error?.error || 'Error al confirmar.'
    });
  }

  forzarConfirmacion(turnoId: number) {
    this.turnoService.confirmarTurno(turnoId, { id: this.pacienteId }, true).subscribe({
      next: () => {
        this.successMessage = 'Turno confirmado correctamente.';
        this.cargarTurnos(this.page);
      },
      error: (err) => this.errorMessage = err.error?.error || 'Error al confirmar.'
    });
  }

  cancelarReserva(turno: any) {
    this.modalService.confirm('Deshacer Reserva', '¿Estás seguro de que querés deshacer esta reserva?', 'El turno volverá a estar disponible para otros pacientes.')
      .then(() => {
        this.turnoService.cancelarReserva(turno.id, this.pacienteId!).subscribe({
          next: () => {
            this.successMessage = 'Reserva deshecha.';
            this.cargarTurnos(this.page);
          },
          error: (err) => this.errorMessage = 'Error al deshacer la reserva.'
        });
      })
      .catch(() => {});
  }

  cancelarTurno(turno: any) {
    this.modalService.confirm('Cancelar Turno', '¿Estás seguro de que querés cancelar este turno?', 'Esta acción no se puede deshacer.')
      .then(() => {
        this.turnoService.delete(turno.id).subscribe({
          next: () => {
            this.successMessage = 'Turno cancelado.';
            this.cargarTurnos(this.page);
          },
          error: (err) => this.errorMessage = 'Error al cancelar el turno.'
        });
      })
      .catch(() => {});
  }

  iniciarReprogramacion(turno: any) {
    this.errorMessage = '';
    this.turnoAReprogramar = turno;
    this.turnoService.getTurnosParaReprogramar(turno.id).subscribe({
      next: (res: any) => {
        this.opcionesReprogramar = res.data;
        this.reprogramacionActiva = true;
      },
      error: (err) => this.errorMessage = err.error?.error || 'Error al buscar opciones.'
    });
  }

  cerrarReprogramacion() {
    this.reprogramacionActiva = false;
    this.opcionesReprogramar = [];
    this.turnoAReprogramar = null;
    this.turnoSeleccionadoParaReprogramar = null;
  }

  seleccionarOpcion(opcion: any) {
    this.turnoSeleccionadoParaReprogramar = opcion;
  }

  ejecutarReprogramacion() {
    if (!this.turnoSeleccionadoParaReprogramar || !this.pacienteId) return;

    this.turnoService.reservarTurno(this.turnoSeleccionadoParaReprogramar.id, { id: this.pacienteId }).subscribe({
      next: (res) => {
        
        this.turnoService.delete(this.turnoAReprogramar.id).subscribe({
          next: () => {
             
             this.confirmarNuevo(this.turnoSeleccionadoParaReprogramar.id);
          },
          error: () => this.errorMessage = 'Error al cancelar el turno original.'
        });
      },
      error: (err) => this.errorMessage = err.error?.error || 'Ese horario ya fue tomado por otro paciente.'
    });
  }

  confirmarNuevo(nuevoId: number) {
    this.turnoService.confirmarTurno(nuevoId, { id: this.pacienteId }).subscribe({
      next: (res) => {
        if (res.requiereConfirmacion) {
          this.modalService.confirm('Advertencia', res.advertencia, '¿Querés confirmar de todas formas?')
            .then(() => {
              this.forzarConfirmacionNuevo(nuevoId);
            })
            .catch(() => {
               this.cerrarReprogramacion();
               this.cargarTurnos();
               this.successMessage = 'Turno reservado (falta confirmación definitiva).';
            });
        } else {
          this.cerrarReprogramacion();
          this.cargarTurnos();
          this.successMessage = 'Turno reprogramado exitosamente.';
        }
      },
      error: () => this.errorMessage = 'Error al confirmar el nuevo turno.'
    });
  }

  forzarConfirmacionNuevo(nuevoId: number) {
    this.turnoService.confirmarTurno(nuevoId, { id: this.pacienteId }, true).subscribe({
      next: () => {
         this.cerrarReprogramacion();
         this.cargarTurnos();
         this.successMessage = 'Turno reprogramado exitosamente.';
      },
      error: () => this.errorMessage = 'Error al confirmar el nuevo turno.'
    });
  }
}
