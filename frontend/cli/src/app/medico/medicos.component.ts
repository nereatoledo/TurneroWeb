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
  templateUrl: './medicos.component.html',
  styleUrl: './medicos.component.css'
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
      .subscribe((dataPackage: DataPackage) => {
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