import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CentroAtencionService } from '../centro/centro-atencion.service';
import { CommonModule } from '@angular/common';
import { CentroAtencion } from '../centro/centro-atencion';
import { DataPackage } from '../data-package';

@Component({
    selector: 'app-centro-especialidades',
    standalone: true,
    imports: [CommonModule, RouterModule],
    template: `
    <div class="container">
      <div class="mb-3">
        <h2>Especialidades de {{ centroNombre }}</h2>
      </div>

      <button class="btn btn-success mb-3">
        Asociar Especialidad
      </button>

      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>#</th>
              <th>Nombre de la Especialidad</th>
              <th class="text-right">Operaciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let especialidad of especialidades; let i = index">
              <td>{{ i + 1 }}</td>
              <td>{{ especialidad }}</td>
              <td class="text-right">
                <button class="btn btn-sm btn-danger">
                  <i class="fa fa-unlink"></i> Desasociar
                </button>
              </td>
            </tr>
            <tr *ngIf="especialidades.length === 0">
              <td colspan="3" class="text-center py-4 text-muted">
                No hay especialidades asociadas a este centro.
              </td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="3">
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
export class CentroEspecialidadesComponent implements OnInit {
    especialidades: string[] = [];
    idCentro!: number;
    centroNombre: string = 'Cargando...';

    constructor(
        private route: ActivatedRoute,
        private centroService: CentroAtencionService
    ) { }

    ngOnInit(): void {
        this.idCentro = Number(this.route.snapshot.paramMap.get('id'));
        this.getCentroNombre();
        this.cargarEspecialidades();
    }

    getCentroNombre(): void {
        if (this.idCentro) {
            this.centroService.get(this.idCentro.toString()).subscribe((dataPackage: DataPackage) => {
                const centro = <CentroAtencion>dataPackage.data;
                this.centroNombre = centro.nombre;
            });
        }
    }

    cargarEspecialidades(): void {
        this.centroService.obtenerEspecialidadesDeCentro(this.idCentro).subscribe({
            next: (dataPackage: DataPackage) => {
                this.especialidades = <string[]>dataPackage.data;
            },
            error: (err) => {
                console.error('Error al cargar las especialidades:', err);
            }
        });
    }
}