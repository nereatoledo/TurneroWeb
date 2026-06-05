import { Component, OnInit } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { CommonModule, Location } from "@angular/common";
import { FormsModule } from "@angular/forms";
import { NgbTypeaheadModule } from "@ng-bootstrap/ng-bootstrap";
import { Observable, of } from "rxjs";
import { catchError, debounceTime, distinctUntilChanged, map, switchMap } from "rxjs/operators";

import { Especialidad } from "./especialidades";
import { EspecialidadService } from "./especialidad.service";
import { ModalService } from "../modal/modal.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { DataPackage } from "../data-package";

@Component({
  selector: "app-especialidades", 
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent, FormsModule, NgbTypeaheadModule],
  templateUrl: './especialidades.component.html',
  styleUrl: './especialidades.component.css'
})
export class EspecialidadesComponent implements OnInit {
  especialidades: Especialidad[] = [];
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  isAdmin: boolean = false;

  terminoBusqueda: any;

  constructor(
    private especialidadService: EspecialidadService,
    private modalService: ModalService,
    private router: Router,
    private location: Location
  ) { }

  ngOnInit(): void {
    this.isAdmin = this.router.url.includes('/admin');
    this.getEspecialidades();
  }

  searchEspecialidad = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => {
        if (term.length < 1) { 
          return of([]);
        }
        return this.especialidadService.byPage(1, 1000).pipe(
          map((dp: DataPackage) => {
            const page = dp.data as ResultsPage;
            const items = page.content as Especialidad[];
            return items.filter(e => e.nombre.toLowerCase().includes(term.toLowerCase()));
          }),
          catchError(() => of([]))
        );
      })
    );

  resultFormatEsp = (value: Especialidad) => value.nombre;
  inputFormatEsp = (value: Especialidad) => value.nombre;

  onEspecialidadSelected(event: any): void {
    const seleccionada = event.item as Especialidad;
    this.resultsPage.content = [seleccionada];
    this.resultsPage.totalPages = 1;
    this.resultsPage.number = 1;
  }

  checkSearchClear(term: any): void {
    if (!term || typeof term === 'string' && term.trim() === '') {
      this.getEspecialidades();
    }
  }

  limpiarBusqueda(): void {
    this.terminoBusqueda = '';
    this.getEspecialidades();
  }

  get typeofTermino(): string {
    return typeof this.terminoBusqueda;
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
              this.terminoBusqueda = ''; 
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

  goBack(): void {
    this.location.back();
  }
}