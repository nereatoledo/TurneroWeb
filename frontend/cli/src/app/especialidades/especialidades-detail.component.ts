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
  templateUrl: './especialidades-detail.component.html',
  styleUrl: './especialidades-detail.component.css'
})
export class EspecialidadDetailComponent implements OnInit {
  especialidad: Especialidad = <Especialidad>{
    nombre: "",
    descripcion: "",
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