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
  templateUrl: './especialidades.component.html',
  styleUrl: './especialidades.component.css'
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
      .subscribe((dataPackage: DataPackage) => {
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