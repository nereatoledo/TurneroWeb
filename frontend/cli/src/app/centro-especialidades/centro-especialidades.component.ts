import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CentroAtencionService } from '../centro/centro-atencion.service';
import { CommonModule } from '@angular/common';
import { CentroAtencion } from '../centro/centro-atencion';
import { DataPackage } from '../data-package';
import { ModalService } from '../modal/modal.service';
import { FormsModule } from '@angular/forms';
import { NgbTypeaheadModule } from '@ng-bootstrap/ng-bootstrap';
import { EspecialidadService } from '../especialidades/especialidad.service';
import { catchError, debounceTime, distinctUntilChanged, map, Observable, of, switchMap } from 'rxjs';

@Component({
    selector: 'app-centro-especialidades',
    standalone: true,
    imports: [CommonModule, RouterModule, FormsModule, NgbTypeaheadModule],
    template: `
    <div class="container">
      <div class="mb-3">
        <h2>Especialidades de {{ centroNombre }}</h2>
      </div>

      <button *ngIf="!mostrandoFormulario" class="btn btn-success mb-3" (click)="mostrandoFormulario = true">
        <i class="fa fa-plus"></i> Asociar Especialidad
      </button>

      <div *ngIf="mostrandoFormulario" class="d-flex align-items-center p-3 mb-4 bg-light border rounded shadow-sm" style="max-width: 800px; gap: 15px;">
        <label for="especialidad" class="mb-0 font-weight-bold" style="white-space: nowrap;">
          Asociar especialidad:
        </label>
        
        <div class="flex-grow-1">
          <input
            id="especialidad"
            type="text"
            class="form-control"
            [(ngModel)]="especialidadSeleccionada"
            [ngbTypeahead]="searchEspecialidad"
            [editable]="false"
            [resultFormatter]="resultFormat"
            [inputFormatter]="inputFormat"
            placeholder="Buscar..."
            autocomplete="off"
          />
        </div>

        <button class="btn btn-primary" (click)="asociar()" [disabled]="!especialidadSeleccionada?.id">
          Guardar
        </button>
        
        <button class="btn btn-secondary" (click)="cancelar()">
          Cancelar
        </button>
      </div>

      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre de la Especialidad</th>
              <th class="text-right">Operaciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let especialidad of especialidades">
              <td class="font-weight-bold">{{ especialidad.id }}</td>
              <td>{{ especialidad.nombre }}</td>
              <td class="text-right">
                <button class="btn btn-sm btn-danger" (click)="desasociar(especialidad)">
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
    especialidades: any[] = [];
    idCentro!: number;
    centroNombre: string = 'Cargando...';

    mostrandoFormulario: boolean = false;
    especialidadSeleccionada: any = null;

    constructor(
        private route: ActivatedRoute,
        private centroService: CentroAtencionService,
        private modalService: ModalService,
        private espService: EspecialidadService
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
                let data = <any[]>dataPackage.data;
                this.especialidades = data.sort((a, b) => b.id - a.id);
            },
            error: (err) => {
                console.error('Error al cargar las especialidades:', err);
            }
        });
    }

    desasociar(especialidad: any): void {
        if (!especialidad.id) {
            this.modalService.confirm("Error", "No se encontró el ID de la especialidad.", "");
            return;
        }

        this.modalService
            .confirm(
                "Desasociar Especialidad",
                `¿Está seguro de que desea desasociar la especialidad "${especialidad.nombre}"?`,
                "Esta acción se puede revertir luego asociándola nuevamente."
            )
            .then(() => {
                this.centroService.desasociarEspecialidad(this.idCentro, especialidad.id).subscribe({
                    next: (dataPackage: DataPackage) => {
                        this.cargarEspecialidades();
                        this.modalService.info("¡Éxito!", dataPackage.message, "");
                    },
                    error: (err) => {
                        const errorResponse: DataPackage = err.error;
                        this.modalService.confirm("Error:", errorResponse?.message || "Ocurrió un error inesperado", "");
                    }
                });
            })
            .catch(() => { });
    }
    
    searchEspecialidad = (text$: Observable<string>): Observable<any[]> =>
        text$.pipe(
            debounceTime(300),
            distinctUntilChanged(),
            switchMap((term) =>
                this.espService.search(term).pipe(
                    map((response) => <any[]>response.data),
                    catchError(() => of([]))
                )
            )
        );

    resultFormat(value: any) {
        return value.nombre;
    }

    inputFormat(value: any) {
        return value ? value.nombre : null;
    }

    cancelar() {
        this.mostrandoFormulario = false;
        this.especialidadSeleccionada = null;
    }

    asociar() {
        if (!this.especialidadSeleccionada || !this.especialidadSeleccionada.id) return;

        this.centroService.asociarEspecialidad(this.idCentro, this.especialidadSeleccionada.id).subscribe({
            next: (dataPackage: DataPackage) => {
                this.modalService.info("¡Éxito!", dataPackage.message, "");
                this.cargarEspecialidades(); 
                this.cancelar(); 
            },
            error: (err) => {
                const errorResponse: DataPackage = err.error;
                this.modalService.confirm("Aviso", errorResponse?.message || "Ocurrió un error al intentar asociar.", "");
            }
        });
    }
}