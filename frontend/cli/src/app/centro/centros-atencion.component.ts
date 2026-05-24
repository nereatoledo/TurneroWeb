import { Component, OnInit } from "@angular/core";
import { RouterModule } from "@angular/router";
import { CentroAtencion } from "./centro-atencion";
import { CentroAtencionService } from "./centro-atencion.service";
import { ModalService } from "../modal/modal.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { DataPackage } from "../data-package";
import { CommonModule, Location } from "@angular/common";

@Component({
  selector: "app-customer",
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
    <div class="container">
      <h2>Centros de Atención</h2>
      
      <a routerLink="/centros_atencion/new" class="btn btn-success mb-3">
        Nuevo Centro de Atención
      </a>
      
      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>#</th>
              <th>Nombre</th>
              <th class="text-right">Operaciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let centro_atencion of resultsPage.content">
              <td>{{ centro_atencion.id }}</td>
              <td>{{ centro_atencion.nombre }}</td>
              <td class="text-right">
                
                <a
                  [routerLink]="['/centros_atencion', centro_atencion.id, 'consultorios']"
                  class="btn btn-sm btn-info mr-2"
                >
                  <i class="fa fa-list"></i> Consultorios
                </a>

                <a
                  [routerLink]="['/centros_atencion', centro_atencion.id, 'especialidades']"
                  class="btn btn-sm btn-secondary mr-2"
                >
                  <i class="fa fa-tags"></i> Especialidades
                </a>

                <a
                  [routerLink]="['/centros_atencion', centro_atencion.id, 'medicos']"
                  class="btn btn-sm btn-warning mr-2"
                >
                  <i class="fa fa-user-md"></i> Médicos
                </a>

                <a
                  [routerLink]="['/centros_atencion', centro_atencion.id]"
                  class="btn btn-sm btn-primary mr-2"
                >
                  <i class="fa fa-pencil"></i> Editar
                </a>
                
                <button
                  (click)="remove(centro_atencion)"
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
                    [hidden]="resultsPage.numberOfElements < 0"
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
export class CentrosAtencionComponent implements OnInit {
  centros_atencion: CentroAtencion[] = [];
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;

  constructor(
    private centro_atencionService: CentroAtencionService,
    private modalService: ModalService,
  ) { }

  getCentros(): void {
    this.centro_atencionService
      .byPage(this.currentPage, 10)
      .subscribe((dataPackage) => {
        this.resultsPage = <ResultsPage>dataPackage.data;
      });
  }

  remove(centro_atencion: CentroAtencion): void {
    this.modalService
      .confirm(
        "Eliminar centro de atención",
        `¿Está seguro de que desea eliminar a ${centro_atencion.nombre}?`,
        "Esta acción no se puede deshacer.",
      )
      .then(() => {
        if (centro_atencion.id != null) {
          this.centro_atencionService.remove(centro_atencion.id).subscribe({
            next: (dataPackage: DataPackage) => {
              this.getCentros();
              this.modalService.info("¡Éxito!", dataPackage.message, "");
            }
          });
        }
      });
  }

  onPageChangeRequested(page: number): void {
    this.currentPage = page;
    this.getCentros();
  }

  ngOnInit(): void {
    this.getCentros();
  }
}