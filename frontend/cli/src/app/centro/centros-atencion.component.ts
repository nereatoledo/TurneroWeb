import { Component, OnInit } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { Location, CommonModule } from "@angular/common"; // 1. Importamos Location
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
  templateUrl: './centros-atencion.component.html',
  styleUrl: './centros-atencion.component.css'
})
export class CentrosAtencionComponent implements OnInit {
  centros_atencion: CentroAtencion[] = [];
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  isAdmin: boolean = false;
  prefix: string = '/usuario';

  constructor(
    private centro_atencionService: CentroAtencionService,
    private modalService: ModalService,
    private router: Router,
    private location: Location
  ) { }

  getCentros(): void {
    this.centro_atencionService
      .byPage(this.currentPage, 10)
      .subscribe((dataPackage: DataPackage) => {
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
    this.isAdmin = this.router.url.includes('/admin');
    this.prefix = this.isAdmin ? '/admin' : '/usuario'; 
    this.getCentros();
  }

  goBack(): void {
    this.location.back();
  }
}