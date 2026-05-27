import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CommonModule, Location } from "@angular/common";
import { Medico } from "./medico";
import { MedicoService } from "./medico.service";
import { EspecialidadService } from "../especialidades/especialidad.service";
import { ActivatedRoute } from "@angular/router";
import { ModalService } from "../modal/modal.service";
import { DataPackage } from "../data-package";
import { NgbTypeaheadModule } from "@ng-bootstrap/ng-bootstrap";
import { catchError, debounceTime, distinctUntilChanged, map, Observable, of, switchMap } from "rxjs";

@Component({
  selector: "app-medico-detail",
  standalone: true,
  imports: [FormsModule, CommonModule, NgbTypeaheadModule],
  templateUrl: './medico-detail.component.html',
  styleUrl: './medico-detail.component.css'
})
export class MedicoDetailComponent implements OnInit {
  medico: Medico = {
    nombre: "",
    apellido: "",
    dni: "",
    matricula: "",
    especialidad: <any>null
  };

  constructor(
    private medicoService: MedicoService,
    private espService: EspecialidadService,
    private route: ActivatedRoute,
    private location: Location,
    private modalService: ModalService
  ) { }

  ngOnInit() {
    this.get();
  }

  get(): void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id && id !== "new") {
      this.medicoService.get(id).subscribe(dp => {
        this.medico = <Medico>dp.data;
      });
    }
  }

  searchEspecialidad = (text$: Observable<string>): Observable<any[]> =>
    text$.pipe(
      debounceTime(300),
      distinctUntilChanged(),
      switchMap((term) =>
        this.espService.search(term).pipe(
          map((response) => <any[]>response.data),
          catchError(() => of([]))
        )
      )
    );

  resultFormat(value: any) {
    return value.nombre;
  }

  inputFormat(value: any) {
    return value ? value.nombre : null;
  }

  save(): void {
    this.medicoService.save(this.medico).subscribe({
      next: (dataPackage: DataPackage) => {
        this.modalService.info("¡Éxito!", dataPackage.message, "");
        this.goBack();
      },
      error: (err) => {
        const errorResponse: DataPackage = err.error;
        if (err.status == 409 || err.status == 400) {
          this.modalService.confirm("Error:", errorResponse.message, "");
        }
      },
    });
  }
  
  goBack() { this.location.back(); }
}