import { Component, OnInit } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { Location, CommonModule } from "@angular/common";
import { Medico } from "./medico";
import { MedicoService } from "./medico.service";
import { ModalService } from "../modal/modal.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { DataPackage } from "../data-package";

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
  isAdmin: boolean = false;

  constructor(
    private medicoService: MedicoService,
    private modalService: ModalService,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.router.url.includes('/admin');
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

  goBack(): void {
    this.location.back();
  }
}