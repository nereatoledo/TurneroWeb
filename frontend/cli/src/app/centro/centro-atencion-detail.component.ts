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
  templateUrl: './centro-atencion-detail.component.html',
  styleUrl: './centro-atencion-detail.component.css'
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