import { CommonModule } from "@angular/common";
import { Component, OnInit } from "@angular/core";
import { ActivatedRoute, RouterModule } from "@angular/router";
import { Consultorio } from "./consultorio";
import { ConsultorioService } from "./consultorio.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { ModalService } from "../modal/modal.service";
import { CentroAtencionService } from "../centro/centro-atencion.service";
import { CentroAtencion } from "../centro/centro-atencion";

@Component({
    selector: "app-consultorio",
    standalone: true,
    imports: [CommonModule, RouterModule, PaginationComponent],
    template: `
    <div class="container">
      <div class="mb-3">
        <h2>Lista de Consultorios de {{ centroNombre }}</h2>
      </div>

      <a [routerLink]="['/centros_atencion', centroId, 'consultorios', 'new']" class="btn btn-success mb-3">
        Nuevo Consultorio
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
            <tr *ngFor="let consultorio of resultsPage.content">
              <td>{{ consultorio.id }}</td>
              <td>{{ consultorio.nombre }}</td>
              <td class="text-right">
                <a
                  [routerLink]="['/centros_atencion', centroId, 'consultorios', consultorio.id]"
                  class="btn btn-sm btn-primary mr-2"
                >
                  <i class="fa fa-pencil"></i> Editar
                </a>
                <button
                  (click)="remove(consultorio)"
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
                <div class="d-flex align-items-center mt-3">
                  
                  <div style="flex: 1;">
                    <button routerLink="/centros_atencion" class="btn btn-danger">
                      <i class="fa fa-arrow-left"></i> Volver a Centros
                    </button>
                  </div>

                  <app-pagination
                    [totalPages]="resultsPage.totalPages"
                    [currentPage]="currentPage"
                    (pageChangeRequested)="onPageChangeRequested($event)"
                    [number]="resultsPage.number"
                    [hidden]="resultsPage.numberOfElements < 1"
                  >
                  </app-pagination>

                  <div style="flex: 1;"></div>
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
export class ConsultorioComponent implements OnInit {
    resultsPage: ResultsPage = <ResultsPage>{};
    currentPage: number = 1;
    centroId: string | null = null;
    centroNombre: string = "";

    constructor(
        private consultorioService: ConsultorioService,
        private centroService: CentroAtencionService,
        private route: ActivatedRoute,
        private modalService: ModalService
    ) { }

    ngOnInit(): void {
        this.centroId = this.route.snapshot.paramMap.get("id");
        this.getCentroNombre();
        this.getConsultorios();
    }

    getCentroNombre(): void {
        if (this.centroId) {
            this.centroService.get(this.centroId).subscribe((dataPackage) => {
                const centro = <CentroAtencion>dataPackage.data;
                this.centroNombre = centro.nombre;
            });
        }
    }

    getConsultorios(): void {
        if (this.centroId) {
            this.consultorioService
                .listByCentro(this.centroId, this.currentPage, 10)
                .subscribe((dataPackage) => {
                    this.resultsPage = <ResultsPage>dataPackage.data;
                });
        }
    }

    remove(consultorio: Consultorio): void {
        this.modalService
            .confirm(
                "Eliminar consultorio",
                `¿Está seguro de que desea eliminar el consultorio ${consultorio.nombre}?`,
                "Esta acción no se puede deshacer."
            )
            .then(() => {
                if (consultorio.id != null) {
                    this.consultorioService.remove(consultorio.id).subscribe({
                        next: (dataPackage) => {
                            this.getConsultorios();
                            this.modalService.info("¡Éxito!", dataPackage.message, "");
                        },
                    });
                }
            });
    }

    onPageChangeRequested(page: number): void {
        this.currentPage = page;
        this.getConsultorios();
    }
}