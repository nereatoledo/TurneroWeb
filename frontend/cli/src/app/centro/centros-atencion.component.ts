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
  templateUrl: './centros-atencion.component.html',
  styleUrl: './centros-atencion.component.css'
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
    this.getCentros();
  }
}