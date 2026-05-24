import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CentroAtencionService } from '../centro/centro-atencion.service';
import { CommonModule } from '@angular/common';
import { CentroAtencion } from '../centro/centro-atencion';
import { DataPackage } from '../data-package';
import { ModalService } from '../modal/modal.service'; 

@Component({
    selector: 'app-centro-medicos',
    standalone: true,
    imports: [CommonModule, RouterModule],
    template: `
    <div class="container">
      <div class="mb-3">
        <h2>Médicos de {{ centroNombre }}</h2>
      </div>

      <button class="btn btn-success mb-3">
        Asociar Médico
      </button>

      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>#</th>
              <th>Nombre del Médico</th>
              <th>Matrícula</th>
              <th class="text-right">Operaciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let medico of medicos; let i = index">
              <td>{{ i + 1 }}</td>
              <td>{{ medico.apellido }}, {{ medico.nombre }}</td>
              <td>{{ medico.matricula }}</td>
              <td class="text-right">
                
                <button class="btn btn-sm btn-danger" (click)="desasociar(medico)">
                  <i class="fa fa-unlink"></i> Desasociar
                </button>
                
              </td>
            </tr>
            <tr *ngIf="medicos.length === 0">
              <td colspan="4" class="text-center py-4 text-muted">
                No hay médicos asociados a este centro.
              </td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="4">
                <div class="d-flex align-items-center mt-3">
                  <div style="flex: 1;">
                    <button routerLink="/centros_atencion" class="btn btn-secondary">
                      <i class="fa fa-arrow-left"></i> Volver a Centros
                    </button>
                  </div>
                </div>
              </td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
    `,
    styles: []
})
export class CentroMedicosComponent implements OnInit {
    medicos: any[] = [];
    idCentro!: number;
    centroNombre: string = 'Cargando...';

    constructor(
        private route: ActivatedRoute,
        private centroService: CentroAtencionService,
        private modalService: ModalService // Inyectamos el servicio de modales
    ) { }

    ngOnInit(): void {
        this.idCentro = Number(this.route.snapshot.paramMap.get('id'));
        this.getCentroNombre();
    }

    getCentroNombre(): void {
        if (this.idCentro) {
            this.centroService.get(this.idCentro.toString()).subscribe((dataPackage: DataPackage) => {
                const centro = <CentroAtencion>dataPackage.data;
                this.centroNombre = centro.nombre;
                this.cargarMedicos(this.centroNombre);
            });
        }
    }

    cargarMedicos(nombreCentro: string): void {
        this.centroService.obtenerMedicosDeCentro(nombreCentro).subscribe({
            next: (dataPackage: DataPackage) => {
                this.medicos = <any[]>dataPackage.data;
            },
            error: (err) => {
                console.error('Error al cargar los médicos:', err);
            }
        });
    }

    desasociar(medico: any): void {
        if (!medico.id) {
            this.modalService.confirm("Error", "No se encontró el ID del médico.", "");
            return;
        }

        this.modalService
            .confirm(
                "Desasociar Médico",
                `¿Está seguro de que desea desasociar al Dr./Dra. ${medico.apellido} de este centro?`,
                "Esta acción se puede revertir luego asociándolo nuevamente."
            )
            .then(() => {
                this.centroService.desasociarMedico(this.centroNombre, medico.id).subscribe({
                    next: (dataPackage: DataPackage) => {
                        this.cargarMedicos(this.centroNombre); 
                        this.modalService.info("¡Éxito!", dataPackage.message, "");
                    },
                    error: (err) => {
                        const errorResponse: DataPackage = err.error;
                        this.modalService.confirm("Error:", errorResponse?.message || "Ocurrió un error inesperado", "");
                    }
                });
            })
            .catch(() => {
            });
    }
}