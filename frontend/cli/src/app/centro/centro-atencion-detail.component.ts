import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CommonModule, Location, UpperCasePipe } from "@angular/common";
import { CentroAtencion } from "./centro-atencion";
import { CentroAtencionService } from "./centro-atencion.service";
import { ActivatedRoute } from "@angular/router";
import { ModalService } from "../modal/modal.service";
import { DataPackage } from "../data-package";

@Component({
  selector: "app-centro-atencion-detail",
  standalone: true,
  imports: [FormsModule, CommonModule, UpperCasePipe],
  template: `
    <div class="container mt-4">
      <div *ngIf="centro_atencion">
        <h2>{{ centro_atencion.nombre || "Nuevo Centro" | uppercase }}</h2>
        <form #form="ngForm">
          <div class="form-group mb-3">
            <label for="nombre">Nombre:</label>
            <input
              [(ngModel)]="centro_atencion.nombre"
              name="nombre"
              class="form-control"
              required
              #nombre="ngModel"
            />
            <div
              *ngIf="nombre.invalid && (nombre.dirty || nombre.touched)"
              class="text-danger"
            >
              El nombre es requerido.
            </div>
          </div>

          <div class="form-group mb-3">
            <label for="direccion">Dirección:</label>
            <input
              [(ngModel)]="centro_atencion.direccion"
              name="direccion"
              class="form-control"
              required
              #direccion="ngModel"
            />
            <div
              *ngIf="
                direccion.invalid && (direccion.dirty || direccion.touched)
              "
              class="text-danger"
            >
              La dirección es requerida.
            </div>
          </div>

          <div class="form-group mb-3">
            <label for="telefono">Teléfono:</label>
            <input
              [(ngModel)]="centro_atencion.telefono"
              name="telefono"
              class="form-control"
              required
              #telefono="ngModel"
              placeholder="Ej: 0280 445-XXXX"
            />
            <div
              *ngIf="telefono.invalid && (telefono.dirty || telefono.touched)"
              class="text-danger"
            >
              El teléfono es requerido.
            </div>
          </div>

          <div class="form-group mb-3">
            <label for="localidad">Localidad:</label>
            <input
              [(ngModel)]="centro_atencion.localidad"
              name="localidad"
              class="form-control"
              required
              #localidad="ngModel"
            />
            <div
              *ngIf="
                localidad.invalid && (localidad.dirty || localidad.touched)
              "
              class="text-danger"
            >
              La localidad es requerida.
            </div>
          </div>

          <div class="form-group mb-3">
            <label for="provincia">Provincia:</label>
            <input
              [(ngModel)]="centro_atencion.provincia"
              name="provincia"
              class="form-control"
              required
              #provincia="ngModel"
            />
            <div
              *ngIf="
                provincia.invalid && (provincia.dirty || provincia.touched)
              "
              class="text-danger"
            >
              La provincia es requerida.
            </div>
          </div>

          <div class="row">
            <div class="form-group mb-3 col-md-6">
              <label for="latitud">Latitud:</label>
              <input
                type="number"
                step="any"
                [(ngModel)]="centro_atencion.coordenadas.latitud"
                name="latitud"
                class="form-control"
                required
                #latitud="ngModel"
              />
              <div
                *ngIf="latitud.invalid && (latitud.dirty || latitud.touched)"
                class="text-danger"
              >
                La latitud es requerida.
              </div>
            </div>
            <div class="form-group mb-3 col-md-6">
              <label for="longitud">Longitud:</label>
              <input
                type="number"
                step="any"
                [(ngModel)]="centro_atencion.coordenadas.longitud"
                name="longitud"
                class="form-control"
                required
                #longitud="ngModel"
              />
              <div
                *ngIf="longitud.invalid && (longitud.dirty || longitud.touched)"
                class="text-danger"
              >
                La longitud es requerida.
              </div>
            </div>
          </div>

          <button (click)="goBack()" class="btn btn-danger mt-3 mr-2">
            Atrás
          </button>
          <button
            (click)="save()"
            class="btn btn-success mt-3"
            [disabled]="form.invalid"
          >
            Guardar
          </button>
        </form>
      </div>
    </div>
  `,
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
        this.modalService.confirm("¡Éxito!", dataPackage.message, "");
        this.goBack();
      },
      error: (err) => {
        const errorResponse: DataPackage = err.error;

        if (err.status === 409) {
          this.modalService.confirm(
            "Error al crear:",
            errorResponse.message,
            "",
          );
        } else {
          console.error("Error inesperado", err);
        }
      },
    });
  }
}
