import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { Medico } from "./medico";
import { MedicoService } from "./medico.service";
import { ModalService } from "../modal/modal.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { DataPackage } from "../data-package";
import { CommonModule } from "@angular/common";

@Component({
    selector: "app-medicos",
    standalone: true,
    imports: [CommonModule, RouterModule, PaginationComponent],
    template: `
    <div class="container">
      <h2>Médicos</h2>
      
      <a routerLink="/medicos/new" class="btn btn-success mb-3">
        Nuevo Médico
      </a>
      
      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nombre Completo</th>
              <th>DNI</th>
              <th>Matrícula</th>
              <th>Especialidad</th>
              <th>Operaciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let medico of resultsPage.content">
              <td>{{ medico.id }}</td>
              <td>{{ medico.apellido }}, {{ medico.nombre }}</td>
              <td>{{ medico.dni }}</td>
              <td>{{ medico.matricula }}</td>
              <td>{{ medico.especialidad?.nombre || medico.especialidad }}</td>
              <td class="text-right">
                
                <a
                  [routerLink]="['/medicos', medico.id]"
                  class="btn btn-sm btn-primary mr-2"
                >
                  <i class="fa fa-pencil"></i> Editar
                </a>
                
                <button
                  (click)="remove(medico)"
                  class="btn btn-sm btn-danger"
                >
                  <i class="fa fa-remove"></i> Borrar
                </button>
              </td>
            </tr>
          </tbody>
          <tfoot>
            <tr>
              <td colspan="6">
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
export class MedicosComponent implements OnInit {
    resultsPage: ResultsPage = <ResultsPage>{};
    currentPage: number = 1;

    constructor(
        private medicoService: MedicoService,
        private modalService: ModalService,
    ) { }

    ngOnInit(): void {
        this.getMedicos();
    }

    getMedicos(): void {
        this.medicoService
            .byPage(this.currentPage, 10)
            .subscribe((dataPackage) => {
                this.resultsPage = <ResultsPage>dataPackage.data;
            });
    }

    remove(medico: Medico): void {
        this.modalService
            .confirm(
                "Eliminar médico",
                `¿Está seguro de que desea eliminar al Dr./Dra. ${medico.apellido}?`,
                "Esta acción no se puede deshacer.",
            )
            .then(() => {
                if (medico.id != null) {
                    this.medicoService.remove(medico.id).subscribe({
                        next: (dataPackage: DataPackage) => {
                            this.getMedicos();
                            this.modalService.info("¡Éxito!", dataPackage.message, "");
                        }
                    });
                }
            });
    }

    onPageChangeRequested(page: number): void {
        this.currentPage = page;
        this.getMedicos();
    }
}