import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CommonModule, Location } from "@angular/common";
import { CentroAtencion } from "./centro-atencion";
import { CentroAtencionService } from "./centro-atencion.service";
import { ActivatedRoute } from "@angular/router";
import { ModalService } from "../modal/modal.service";
import { DataPackage } from "../data-package";

@Component({
  selector: "app-centro-atencion-detail",
  standalone: true,
  imports: [FormsModule, CommonModule],
  template: `
    <div class="container py-4 page-animation" style="max-width: 800px;">
      <div *ngIf="centro_atencion">
        
        <h2 class="custom-title mb-4">
          {{ centro_atencion.id ? 'Editar Centro: ' + centro_atencion.nombre : 'Nuevo Centro de Atención' }}
        </h2>

        <div class="form-wrapper">
          <form #form="ngForm">
            
            <div class="form-group mb-4">
              <label for="nombre" class="custom-label">Nombre del Centro</label>
              <input
                [(ngModel)]="centro_atencion.nombre"
                name="nombre"
                class="form-control custom-input"
                required
                #nombre="ngModel"
                placeholder="Ej: Centro Médico Rawson"
              />
              <div *ngIf="nombre.invalid && (nombre.dirty || nombre.touched)" class="text-danger small mt-1">
                El nombre es requerido.
              </div>
            </div>

            <div class="row">
              <div class="form-group mb-4 col-md-8">
                <label for="direccion" class="custom-label">Dirección</label>
                <input
                  [(ngModel)]="centro_atencion.direccion"
                  name="direccion"
                  class="form-control custom-input"
                  required
                  #direccion="ngModel"
                  placeholder="Ej: Av. San Martín 1234"
                />
                <div *ngIf="direccion.invalid && (direccion.dirty || direccion.touched)" class="text-danger small mt-1">
                  La dirección es requerida.
                </div>
              </div>

              <div class="form-group mb-4 col-md-4">
                <label for="telefono" class="custom-label">Teléfono</label>
                <input
                  [(ngModel)]="centro_atencion.telefono"
                  name="telefono"
                  class="form-control custom-input"
                  required
                  pattern="^[0-9 \\-\\+]+$"
                  #telefono="ngModel"
                  placeholder="Ej: 0280 445-XXXX"
                />
                <div *ngIf="telefono.invalid && (telefono.dirty || telefono.touched)" class="text-danger small mt-1">
                  <span *ngIf="telefono.errors?.['required']">El teléfono es requerido.</span>
                  <span *ngIf="telefono.errors?.['pattern']">Formato inválido.</span>
                </div>
              </div>
            </div>

            <div class="row">
              <div class="form-group mb-4 col-md-6">
                <label for="localidad" class="custom-label">Localidad</label>
                <input
                  [(ngModel)]="centro_atencion.localidad"
                  name="localidad"
                  class="form-control custom-input"
                  required
                  #localidad="ngModel"
                  placeholder="Ej: Trelew"
                />
                <div *ngIf="localidad.invalid && (localidad.dirty || localidad.touched)" class="text-danger small mt-1">
                  La localidad es requerida.
                </div>
              </div>

              <div class="form-group mb-4 col-md-6">
                <label for="provincia" class="custom-label">Provincia</label>
                <input
                  [(ngModel)]="centro_atencion.provincia"
                  name="provincia"
                  class="form-control custom-input"
                  required
                  #provincia="ngModel"
                  placeholder="Ej: Chubut"
                />
                <div *ngIf="provincia.invalid && (provincia.dirty || provincia.touched)" class="text-danger small mt-1">
                  La provincia es requerida.
                </div>
              </div>
            </div>

            <hr class="my-4" style="border-color: #e2e8f0;">

            <h5 class="mb-3 text-muted" style="font-size: 1rem; font-weight: 600;">Geolocalización</h5>
            <div class="row">
              <div class="form-group mb-4 col-md-6">
                <label for="latitud" class="custom-label">Latitud</label>
                <input
                  type="number"
                  step="any"
                  [(ngModel)]="centro_atencion.coordenadas.latitud"
                  name="latitud"
                  class="form-control custom-input"
                  required
                  #latitud="ngModel"
                />
              </div>
              <div class="form-group mb-4 col-md-6">
                <label for="longitud" class="custom-label">Longitud</label>
                <input
                  type="number"
                  step="any"
                  [(ngModel)]="centro_atencion.coordenadas.longitud"
                  name="longitud"
                  class="form-control custom-input"
                  required
                  #longitud="ngModel"
                />
              </div>
            </div>

            <div class="d-flex justify-content-end mt-2 pt-3 border-top" style="border-color: #e2e8f0 !important;">
              <button (click)="goBack()" class="btn-action btn-back mr-3" type="button">
                <i class="fa fa-arrow-left"></i> Atrás
              </button>
              
              <button (click)="save()" class="btn-purple-main" [disabled]="form.invalid" type="button">
                <i class="fa fa-save"></i> {{ centro_atencion.id ? 'Actualizar' : 'Guardar' }}
              </button>
            </div>
            
          </form>
        </div>
      </div>
    </div>
  `,
  styles: [`
    /* Animación de entrada */
    .page-animation {
      animation: slideUpFade 0.6s ease-out forwards;
    }
    
    @keyframes slideUpFade {
      0% { opacity: 0; transform: translateY(20px); }
      100% { opacity: 1; transform: translateY(0); }
    }

    /* Título principal */
    .custom-title {
      color: #000000;
      font-family: 'Hackensack', 'Roboto Slab', serif;
      font-weight: bold;
    }

    /* Contenedor tipo tarjeta */
    .form-wrapper {
      background: #ffffff;
      border-radius: 12px;
      padding: 35px 40px;
      box-shadow: 0 5px 20px rgba(0, 0, 0, 0.03);
      border: 1px solid #f0f2f5;
    }

    /* Etiquetas de los campos */
    .custom-label {
      color: #6c757d;
      font-size: 0.85rem;
      font-weight: 600;
      text-transform: uppercase;
      letter-spacing: 0.5px;
      margin-bottom: 8px;
      display: block;
    }

    /* Estilo de los inputs */
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

    /* Efecto al seleccionar el input */
    .custom-input:focus {
      border-color: #8923dc;
      box-shadow: 0 0 0 4px rgba(137, 35, 220, 0.1);
      background-color: #ffffff;
      outline: none;
    }

    /* Estilo del input deshabilitado o de solo lectura */
    .custom-input:disabled {
      background-color: #f8f9fa;
      cursor: not-allowed;
    }

    /* Botón Guardar (Morado) */
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

    /* Botón Atrás (Gris claro) */
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
export class CentroAtencionDetailComponent implements OnInit {
  centro_atencion: CentroAtencion = {
    nombre: "",
    direccion: "",
    localidad: "",
    provincia: "",
    telefono: "",
    coordenadas: { latitud: 0, longitud: 0 },
  };

  constructor(
    private centro_service: CentroAtencionService,
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
      this.centro_service.get(id).subscribe((dataPackage) => {
        this.centro_atencion = <CentroAtencion>dataPackage.data;
      });
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.centro_service.save(this.centro_atencion).subscribe({
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