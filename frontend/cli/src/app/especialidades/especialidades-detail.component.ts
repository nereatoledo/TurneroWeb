import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CommonModule, Location } from "@angular/common";
import { Especialidad } from "./especialidades";
import { EspecialidadService } from "./especialidad.service";
import { ActivatedRoute } from "@angular/router";
import { ModalService } from "../modal/modal.service";
import { DataPackage } from "../data-package";

@Component({
  selector: "app-especialidad-detail",
  standalone: true, 
  imports: [FormsModule, CommonModule],
  template: `
    <div class="container py-4 page-animation" style="max-width: 800px;">
      <div *ngIf="especialidad">
        
        <h2 class="custom-title mb-4">
          {{ especialidad.id ? 'Editar Especialidad: ' + especialidad.nombre : 'Nueva Especialidad' }}
        </h2>

        <div class="form-wrapper">
          <form #form="ngForm">
            
            <div class="form-group mb-4">
              <label for="nombre" class="custom-label">Nombre de la Especialidad</label>
              <input
                [(ngModel)]="especialidad.nombre"
                name="nombre"
                class="form-control custom-input"
                required
                #nombre="ngModel"
                placeholder="Ej: Odontología"
              />
              <div *ngIf="nombre.invalid && (nombre.dirty || nombre.touched)" class="text-danger small mt-1">
                El nombre es requerido.
              </div>
            </div>

            <div class="row">
              <div class="form-group mb-4 col-md-12">
                <label for="detalle" class="custom-label">Detalle / Descripción</label>
                <input
                  [(ngModel)]="especialidad.detalle"
                  name="detalle"
                  class="form-control custom-input"
                  required
                  #detalle="ngModel"
                  placeholder="Ej: Atención integral..."
                />
                <div *ngIf="detalle.invalid && (detalle.dirty || detalle.touched)" class="text-danger small mt-1">
                  El detalle es requerido.
                </div>
              </div>
            </div> 

            <div class="d-flex justify-content-end mt-2 pt-3 border-top" style="border-color: #e2e8f0 !important;">
              <button (click)="goBack()" class="btn-action btn-back mr-3" type="button">
                <i class="fa fa-arrow-left"></i> Atrás
              </button>
              
              <button (click)="save()" class="btn-purple-main" [disabled]="form.invalid" type="button">
                <i class="fa fa-save"></i> {{ especialidad.id ? 'Actualizar' : 'Guardar' }}
              </button>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  `,
  styles: [`
    .page-animation {
      animation: slideUpFade 0.6s ease-out forwards;
    }
    
    @keyframes slideUpFade {
      0% { opacity: 0; transform: translateY(20px); }
      100% { opacity: 1; transform: translateY(0); }
    }

    .custom-title {
      color: #000000;
      font-family: 'Hackensack', 'Roboto Slab', serif;
      font-weight: bold;
    }

    .form-wrapper {
      background: #ffffff;
      border-radius: 12px;
      padding: 35px 40px;
      box-shadow: 0 5px 20px rgba(0, 0, 0, 0.03);
      border: 1px solid #f0f2f5;
    }

    .custom-label {
      color: #6c757d;
      font-size: 0.85rem;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      margin-bottom: 8px;
      display: block;
    }

    .custom-input {
      border: 1.5px solid #e2e8f0;
      border-radius: 8px;
      padding: 10px 15px;
      color: #2d3748;
      font-size: 1rem;
      transition: all 0.3s ease;
      background-color: #fdfdfd;
      box-shadow: none;
    }

    .custom-input:focus {
      border-color: #8923dc;
      box-shadow: 0 0 0 4px rgba(137, 35, 220, 0.1);
      background-color: #ffffff;
      outline: none;
    }

    .custom-input:disabled {
      background-color: #f8f9fa;
      cursor: not-allowed;
    }

    .btn-purple-main {
      background-color: #8923dc;
      color: #ffffff;
      padding: 10px 24px;
      border-radius: 8px;
      font-weight: 500;
      display: inline-flex;
      align-items: center;
      gap: 8px;
      transition: all 0.3s ease;
      box-shadow: 0 4px 10px rgba(137, 35, 220, 0.25);
      border: none;
      cursor: pointer;
    }

    .btn-purple-main:hover:not(:disabled) {
      background-color: #6c1ab3;
      transform: translateY(-2px);
      box-shadow: 0 6px 15px rgba(137, 35, 220, 0.35);
    }

    .btn-purple-main:disabled {
      background-color: #c5a0e5;
      box-shadow: none;
      cursor: not-allowed;
    }

    .btn-back {
      background-color: #f8f9fa;
      color: #4a5568;
      border: 1.5px solid #e2e8f0;
      padding: 10px 20px;
      border-radius: 8px;
      font-weight: 500;
      display: inline-flex;
      align-items: center;
      gap: 8px;
      transition: all 0.2s ease;
      cursor: pointer;
    }

    .btn-back:hover {
      background-color: #e2e8f0;
      color: #2d3748;
    }
  `],
})
export class EspecialidadDetailComponent implements OnInit {
  especialidad: Especialidad = {
    nombre: "",
    detalle: "",
  };

  constructor(
    private especialidad_service: EspecialidadService,
    private route: ActivatedRoute,
    private location: Location,
    private modalService: ModalService,
  ) {}

  ngOnInit() {
    this.get();
  }

  get(): void {
    const id = this.route.snapshot.paramMap.get("id");
    if (id && id !== "new") {
      this.especialidad_service.get(id).subscribe((dataPackage) => {
        this.especialidad = <Especialidad>dataPackage.data;
      });
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.especialidad_service.save(this.especialidad).subscribe({
      next: (dataPackage: DataPackage) => {
        this.modalService.info("¡Éxito!", dataPackage.message, "");
        this.goBack();
      },
      error: (err) => {
        const errorResponse: DataPackage = err.error;
        if (err.status === 409 || err.status === 400) {
          this.modalService.confirm("Error:", errorResponse.message, "");
        } else {
          console.error("Error inesperado", err);
        }
      },
    });
  }
}