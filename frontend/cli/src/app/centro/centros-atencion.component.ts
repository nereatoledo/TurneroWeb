import { CommonModule } from "@angular/common";
import { Component } from "@angular/core";
import { RouterModule } from "@angular/router";
import { CentroAtencion } from "./centro-atencion";
import { CentroAtencionService } from "./centro-atencion.service";
import { ModalService } from "../modal/modal.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { DataPackage } from "../data-package";

@Component({
  selector: "app-customer",
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
    <div class="container">
      <h2>Centros de Atención</h2>
      <a routerLink="/centros_atencion/new" class="btn btn-success mb-3"
        >Nuevo Centro de Atención</a
      >
      <div class="table-responsive">
        <table class="table table-striped table-sm">
          <thead>
            <tr>
              <th>#</th>
              <th>Nombre</th>
              <th>Operaciones</th>
            </tr>
          </thead>
          <tbody>
            <tr *ngFor="let centro_atencion of resultsPage.content">
              <td>{{ centro_atencion.id }}</td>
              <td>{{ centro_atencion.nombre }}</td>
              <td class="text-right">
                <a
                  [routerLink]="['/centros', centro_atencion.id]"
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
            <app-pagination
              [totalPages]="resultsPage.totalPages"
              [currentPage]="currentPage"
              (pageChangeRequested)="onPageChangeRequested($event)"
              [number]="resultsPage.number"
              [hidden]="resultsPage.numberOfElements < 1"
            >
            </app-pagination>
          </tfoot>
        </table>
      </div>
    </div>
  `,
  styles: ``,
})
export class CentrosAtencionComponent {
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
        console.log(this.resultsPage.content);
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
              this.modalService.confirm("¡Éxito!", dataPackage.message, "");
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
