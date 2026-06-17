import { Component, OnInit } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { AgendaService } from '../agenda/agenda.service';
import { CentroAtencionService } from '../centro/centro-atencion.service';
import { MedicoService } from '../medico/medico.service';
import { CentroAtencion } from '../centro/centro-atencion';
import { Medico } from '../medico/medico';

@Component({
  selector: 'app-admin-agendas',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './admin-agendas.component.html',
  styleUrls: ['./admin-agendas.component.css']
})
export class AdminAgendasComponent implements OnInit {
  request: any = {
    nombre: '',
    descripcion: '',
    idMedico: null,
    idCentro: null,
    fechaInicio: '',
    fechaFin: '',
    horaInicio: '',
    horaFin: ''
  };

  centroSeleccionadoNombre: string = '';
  medicoSeleccionadoNombre: string = '';

  centros: CentroAtencion[] = [];
  medicos: Medico[] = [];
  
  errorMessage: string = '';
  successMessage: string = '';

  constructor(
    private agendaService: AgendaService,
    private centroService: CentroAtencionService,
    private medicoService: MedicoService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.centroService.byPage(1, 1000).subscribe((data: any) => this.centros = data.data.content);
    this.medicoService.byPage(1, 1000).subscribe((data: any) => this.medicos = data.data.content);
  }

  getMedicoDisplayName(m: Medico): string {
    return `Dr. ${m.apellido}, ${m.nombre}`;
  }

  onCentroChange() {
    const found = this.centros.find(c => c.nombre === this.centroSeleccionadoNombre);
    this.request.idCentro = found ? found.id : null;
  }

  onMedicoChange() {
    const found = this.medicos.find(m => this.getMedicoDisplayName(m) === this.medicoSeleccionadoNombre);
    this.request.idMedico = found ? found.id : null;
  }

  onSubmit() {
    this.errorMessage = '';
    this.successMessage = '';

    
    const payload = {
        ...this.request,
        idMedico: Number(this.request.idMedico),
        idCentro: Number(this.request.idCentro)
    };

    this.agendaService.autoAsignarAgenda(payload).subscribe({
      next: (res) => {
        this.successMessage = 'Agendas generadas correctamente.';
        setTimeout(() => this.router.navigate(['/admin/centros_atencion/agenda']), 2000);
      },
      error: (err) => {
        if (err.error && err.error.error) {
            this.errorMessage = err.error.error;
        } else {
            this.errorMessage = 'Ocurrió un error al generar las agendas.';
        }
      }
    });
  }
}
