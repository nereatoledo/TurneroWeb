import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { Especialidad } from "./especialidades";
import { EspecialidadService } from "./especialidad.service";
import { ModalService } from "../modal/modal.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { DataPackage } from "../data-package";

@Component({
    selector: "app-especialidades", 
    standalone: true,
    imports: [CommonModule, RouterModule, PaginationComponent],
    template: `
    <div class="container">
      <h2>Especialidades</h2>
      
      <a routerLink="/especialidades/new" class="btn btn-success mb-3">
        Nueva Especialidad
      </a>
      
      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre</th>
              <th>Operaciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let especialidad of resultsPage.content">
              <td>{{ especialidad.id }}</td>
              <td>{{ especialidad.nombre }}</td>
              <td class="text-right">
                <a
                  [routerLink]="['/especialidades', especialidad.id]"
                  class="btn btn-sm btn-primary mr-2"
                >
                  <i class="fa fa-pencil"></i> Editar
                </a>
                
                <button
                  (click)="remove(especialidad)"
                  class="btn btn-sm btn-danger"
                >
                  <i class="fa fa-remove"></i> Borrar
                </button>
              </td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="3">
                <div class="d-flex justify-content-center mt-3">
                  <app-pagination
                    [totalPages]="resultsPage.totalPages"
                    [currentPage]="currentPage"
                    (pageChangeRequested)="onPageChangeRequested($event)"
                    [number]="resultsPage.number"
                    [hidden]="resultsPage.numberOfElements < 1"
                  >
                  </app-pagination>
                </div>
              </td>
            </tr>
          </tfoot>
        </table>
      </div>
    </div>
  `,
    styles: ``,
})
export class EspecialidadesComponent implements OnInit {
    especialidades: Especialidad[] = [];
    resultsPage: ResultsPage = <ResultsPage>{};
    currentPage: number = 1;

    constructor(
        private especialidadService: EspecialidadService,
        private modalService: ModalService,
    ) { }

    ngOnInit(): void {
        this.getEspecialidades();
    }

    getEspecialidades(): void {
        this.especialidadService
            .byPage(this.currentPage, 10)
            .subscribe((dataPackage) => {
                this.resultsPage = <ResultsPage>dataPackage.data;
            });
    }

    remove(especialidad: Especialidad): void {
        this.modalService
            .confirm(
                "Eliminar especialidad",
                `¿Está seguro de que desea eliminar a ${especialidad.nombre}?`,
                "Esta acción no se puede deshacer.",
            )
            .then(() => {
                if (especialidad.id != null) {
                    this.especialidadService.remove(especialidad.id).subscribe({
                        next: (dataPackage: DataPackage) => {
                            this.getEspecialidades();
                            this.modalService.info("¡Éxito!", dataPackage.message, "");
                        }
                    });
                }
            });
    }

    onPageChangeRequested(page: number): void {
        this.currentPage = page;
        this.getEspecialidades();
    }
}