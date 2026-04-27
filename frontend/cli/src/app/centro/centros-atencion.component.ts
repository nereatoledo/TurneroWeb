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
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent],
  template: `
    <div class="container py-4 page-animation">
      
      <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="custom-title m-0">Centros de Atención</h2>
        
        <a routerLink="/centros_atencion/new" class="btn-purple-main text-decoration-none">
          <i class="fa fa-plus"></i> Nuevo Centro
        </a>
      </div>

      <div class="table-wrapper">
        <div class="table-responsive">
          <table class="table custom-table mb-0">
            <thead>
              <tr>
                <th width="10%">ID</th>
                <th width="50%">Nombre</th>
                <th width="40%" class="text-right">Operaciones</th>
              </tr>
            </thead>
            <tbody>
              <tr *ngFor="let centro_atencion of resultsPage.content">
                <td class="text-muted font-weight-bold">{{ centro_atencion.id }}</td>
                <td class="font-weight-medium">{{ centro_atencion.nombre }}</td>
                <td class="text-right">
                  
                  <a [routerLink]="['/centros_atencion', centro_atencion.id]" class="btn-action btn-edit mr-2 text-decoration-none">
                    <i class="fa fa-pencil"></i> Editar
                  </a>
                  
                  <button (click)="remove(centro_atencion)" class="btn-action btn-delete">
                    <i class="fa fa-remove"></i> Borrar
                  </button>

                </td>
              </tr>
            </tbody>
          </table>
        </div>
        
        <div class="mt-4 d-flex justify-content-center" *ngIf="resultsPage.totalPages > 0">
          <app-pagination
            [totalPages]="resultsPage.totalPages"
            [currentPage]="currentPage"
            (pageChangeRequested)="onPageChangeRequested($event)"
            [number]="resultsPage.number"
          >
          </app-pagination>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .page-animation {
      animation: slideUpFade 0.6s ease-out forwards;
    }
    
    @keyframes slideUpFade {
      0% { opacity: 0; transform: translateY(20px); }
      100% { opacity: 1; transform: translateY(0); }
    }

    .custom-title {
      color: #000000;
      font-family: 'Hackensack', 'Roboto Slab', serif;
      font-weight: bold;
    }

    .btn-purple-main {
      background-color: #8923dc;
      color: #ffffff;
      padding: 10px 20px;
      border-radius: 8px;
      font-weight: 500;
      display: inline-flex;
      align-items: center;
      gap: 8px;
      transition: all 0.3s ease;
      box-shadow: 0 4px 10px rgba(137, 35, 220, 0.25);
      border: none;
      cursor: pointer;
    }

    .btn-purple-main:hover {
      background-color: #6c1ab3;
      color: #ffffff;
      transform: translateY(-2px);
      box-shadow: 0 6px 15px rgba(137, 35, 220, 0.35);
    }

    .table-wrapper {
      background: #ffffff;
      border-radius: 12px;
      padding: 24px;
      box-shadow: 0 5px 20px rgba(0, 0, 0, 0.03);
      border: 1px solid #f0f2f5;
    }

    .custom-table {
      width: 100%;
      border-collapse: collapse;
    }

    .custom-table thead th {
      border-top: none;
      border-bottom: 2px solid #8923dc; 
      color: #6c757d;
      font-weight: 600;
      text-transform: uppercase;
      font-size: 0.85rem;
      letter-spacing: 0.5px;
      padding-bottom: 15px;
    }

    .custom-table tbody td {
      vertical-align: middle;
      color: #2d3748;
      padding: 10px 12px; 
      border-bottom: 1px solid #e2e8f0; 
      transition: background-color 0.2s ease;
    }

    .custom-table tbody tr:last-child td {
      border-bottom: none;
    }

    .custom-table tbody tr:hover td {
      background-color: #f8f9fa; 
    }

    .font-weight-medium {
      font-weight: 500;
    }

    .btn-action {
      display: inline-flex;
      align-items: center;
      gap: 6px;
      padding: 6px 14px;
      border-radius: 6px;
      font-size: 0.85rem;
      font-weight: 500;
      transition: all 0.2s ease;
      cursor: pointer;
      border: 1px solid transparent;
      background: transparent;
    }

    .btn-edit {
      color: #8923dc;
      background-color: rgba(137, 35, 220, 0.08); 
    }

    .btn-edit:hover {
      background-color: #8923dc;
      color: #ffffff;
    }

    .btn-delete {
      color: #dc3545;
      background-color: rgba(220, 53, 69, 0.08); 
    }

    .btn-delete:hover {
      background-color: #dc3545;
      color: #ffffff;
    }
  `],
})
export class CentrosAtencionComponent {
  centros_atencion: CentroAtencion[] = [];
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;

  constructor(
    private centro_atencionService: CentroAtencionService,
    private modalService: ModalService,
  ) { }

  ngOnInit(): void {
    this.getCentros();
  }

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
}