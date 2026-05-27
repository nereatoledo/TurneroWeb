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
  templateUrl: './consultorio.component.html',
  styleUrl: './consultorio.component.css'
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
      this.centroService.get(this.centroId).subscribe((dataPackage: import("../data-package").DataPackage) => {
        const centro = <CentroAtencion>dataPackage.data;
        this.centroNombre = centro.nombre;
      });
    }
  }

  getConsultorios(): void {
    if (this.centroId) {
      this.consultorioService
        .listByCentro(this.centroId, this.currentPage, 10)
        .subscribe((dataPackage: import("../data-package").DataPackage) => {
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
            next: (dataPackage: import("../data-package").DataPackage) => {
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