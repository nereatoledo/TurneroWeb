import { Component, OnInit } from "@angular/core";
import { Router, RouterModule } from "@angular/router";
import { Location, CommonModule } from "@angular/common"; // 1. Importamos Location
import { FormsModule } from "@angular/forms";
import { NgbTypeaheadModule } from "@ng-bootstrap/ng-bootstrap";
import { Observable, of } from "rxjs";
import { catchError, debounceTime, distinctUntilChanged, map, switchMap } from "rxjs/operators";

import { CentroAtencion } from "./centro-atencion";
import { CentroAtencionService } from "./centro-atencion.service";
import { ModalService } from "../modal/modal.service";
import { PaginationComponent } from "../pagination/pagination.component";
import { ResultsPage } from "../results-page";
import { DataPackage } from "../data-package";

@Component({
  selector: "app-customer",
  standalone: true,
  imports: [CommonModule, RouterModule, PaginationComponent, FormsModule, NgbTypeaheadModule],
  templateUrl: './centros-atencion.component.html',
  styleUrl: './centros-atencion.component.css'
})
export class CentrosAtencionComponent implements OnInit {
  centros_atencion: CentroAtencion[] = [];
  resultsPage: ResultsPage = <ResultsPage>{};
  currentPage: number = 1;
  isAdmin: boolean = false;
  prefix: string = '/usuario';

  terminoBusqueda: any;

  constructor(
    private centro_atencionService: CentroAtencionService,
    private modalService: ModalService,
    private router: Router,
    private location: Location
  ) { }

  searchCentro = (text$: Observable<string>) =>
    text$.pipe(
      debounceTime(200),
      distinctUntilChanged(),
      switchMap(term => {
        if (term.length < 1) { 
          return of([]);
        }
        return this.centro_atencionService.byPage(1, 1000).pipe(
          map((dp: DataPackage) => {
            const page = dp.data as ResultsPage;
            const items = page.content as CentroAtencion[];
            return items.filter(c => c.nombre.toLowerCase().includes(term.toLowerCase()));
          }),
          catchError(() => of([]))
        );
      })
    );

  resultFormatCentro = (value: CentroAtencion) => value.nombre;
  inputFormatCentro = (value: CentroAtencion) => value.nombre;

  onCentroSelected(event: any): void {
    const seleccionada = event.item as CentroAtencion;
    this.resultsPage.content = [seleccionada];
    this.resultsPage.totalPages = 1;
    this.resultsPage.number = 1;
  }

  checkSearchClear(term: any): void {
    if (!term || typeof term === 'string' && term.trim() === '') {
      this.getCentros();
    }
  }

  limpiarBusqueda(): void {
    this.terminoBusqueda = '';
    this.getCentros();
  }

  get typeofTermino(): string {
    return typeof this.terminoBusqueda;
  }

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