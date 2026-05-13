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
import { catchError, debounceTime, distinctUntilChanged, map, Observable, of, switchMap, tap } from "rxjs";

@Component({
  selector: "app-medico-detail",
  standalone: true,
  imports: [FormsModule, CommonModule, NgbTypeaheadModule],
  template: `
    <div class="container py-4 page-animation" style="max-width: 800px;">
      <div *ngIf="medico">
        
        <h2 class="custom-title mb-4">
          {{ medico.id ? 'Editar Médico: ' + medico.apellido : 'Registrar Nuevo Médico' }}
        </h2>

        <div class="form-wrapper">
          <form #form="ngForm">
            
            <div class="row">
              <div class="form-group mb-4 col-md-6">
                <label for="nombre" class="custom-label">Nombre</label>
                <input
                  [(ngModel)]="medico.nombre"
                  name="nombre"
                  class="form-control custom-input"
                  required
                  #nombre="ngModel"
                />
              </div>

              <div class="form-group mb-4 col-md-6">
                <label for="apellido" class="custom-label">Apellido</label>
                <input
                  [(ngModel)]="medico.apellido"
                  name="apellido"
                  class="form-control custom-input"
                  required
                  #apellido="ngModel"
                />
              </div>
            </div>

            <div class="row">
              <div class="form-group mb-4 col-md-6">
                <label for="dni" class="custom-label">DNI</label>
                <input
                  [(ngModel)]="medico.dni"
                  name="dni"
                  class="form-control custom-input"
                  required
                  pattern="^[0-9]+$"
                  #dni="ngModel"
                />
              </div>

              <div class="form-group mb-4 col-md-6">
                <label for="matricula" class="custom-label">Matrícula</label>
                <input
                  [(ngModel)]="medico.matricula"
                  name="matricula"
                  class="form-control custom-input"
                  required
                  #matricula="ngModel"
                />
              </div>
            </div>

            <div class="form-group mb-4">
              <label for="especialidad" class="custom-label">Especialidad</label>
              <input
                id="especialidad"
                type="text"
                class="form-control custom-input"
                [(ngModel)]="medico.especialidad"
                name="especialidad"
                [ngbTypeahead]="searchEspecialidad"
                [editable]="false"
                [resultFormatter]="resultFormat"
                [inputFormatter]="inputFormat"
                placeholder="Escribí para buscar..."
                required
                autocomplete="off"
                #especialidad="ngModel"
              />
            </div>

            <div class="d-flex justify-content-end mt-2 pt-3 border-top" style="border-color: #e2e8f0 !important;">
              <button (click)="goBack()" class="btn-action btn-back mr-3" type="button">
                <i class="fa fa-arrow-left"></i> Atrás
              </button>
              
              <button (click)="save()" class="btn-purple-main" [disabled]="form.invalid" type="button">
                <i class="fa fa-save"></i> {{ medico.id ? 'Actualizar' : 'Guardar' }}
              </button>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .page-animation { animation: slideUpFade 0.6s ease-out forwards; }
    @keyframes slideUpFade { 0% { opacity: 0; transform: translateY(20px); } 100% { opacity: 1; transform: translateY(0); } }
    .custom-title { color: #000000; font-family: 'Hackensack', 'Roboto Slab', serif; font-weight: bold; }
    .form-wrapper { background: #ffffff; border-radius: 12px; padding: 35px 40px; box-shadow: 0 5px 20px rgba(0, 0, 0, 0.03); border: 1px solid #f0f2f5; }
    .custom-label { color: #6c757d; font-size: 0.85rem; font-weight: 600; text-transform: uppercase; letter-spacing: 0.5px; margin-bottom: 8px; display: block; }
    .custom-input { border: 1.5px solid #e2e8f0; border-radius: 8px; padding: 10px 15px; color: #2d3748; font-size: 1rem; transition: all 0.3s ease; background-color: #fdfdfd; box-shadow: none; }
    .custom-input:focus { border-color: #8923dc; box-shadow: 0 0 0 4px rgba(137, 35, 220, 0.1); background-color: #ffffff; outline: none; }
    .btn-purple-main { background-color: #8923dc; color: #ffffff; padding: 10px 24px; border-radius: 8px; font-weight: 500; display: inline-flex; align-items: center; gap: 8px; transition: all 0.3s ease; box-shadow: 0 4px 10px rgba(137, 35, 220, 0.25); border: none; cursor: pointer; }
    .btn-purple-main:hover:not(:disabled) { background-color: #6c1ab3; transform: translateY(-2px); box-shadow: 0 6px 15px rgba(137, 35, 220, 0.35); }
    .btn-purple-main:disabled { background-color: #c5a0e5; box-shadow: none; cursor: not-allowed; }
    .btn-back { background-color: #f8f9fa; color: #4a5568; border: 1.5px solid #e2e8f0; padding: 10px 20px; border-radius: 8px; font-weight: 500; display: inline-flex; align-items: center; gap: 8px; transition: all 0.2s ease; cursor: pointer; }
    .btn-back:hover { background-color: #e2e8f0; color: #2d3748; }
  `],
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