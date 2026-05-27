import { Component, OnInit } from "@angular/core";
import { FormsModule } from "@angular/forms";
import { CommonModule, Location } from "@angular/common";
import { ActivatedRoute } from "@angular/router";
import { Consultorio } from "./consultorio";
import { ConsultorioService } from "./consultorio.service";
import { ModalService } from "../modal/modal.service";
import { DataPackage } from "../data-package";

@Component({
  selector: "app-consultorio-detail",
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './consultorio-detail.component.html',
  styleUrl: './consultorio-detail.component.css'
})
export class ConsultorioDetailComponent implements OnInit {
  consultorio: Consultorio = {
    nombre: "",
    numero: null as any 
  };
  centroId: string | null = null;

  constructor(
    private consultorioService: ConsultorioService,
    private route: ActivatedRoute,
    private location: Location,
    private modalService: ModalService,
  ) {}

  ngOnInit() {
    this.centroId = this.route.snapshot.paramMap.get("id"); 
    const idConsultorio = this.route.snapshot.paramMap.get("idConsultorio");
    if (idConsultorio && idConsultorio !== "new") {
      this.consultorioService.get(idConsultorio).subscribe((dataPackage) => {
        this.consultorio = <Consultorio>dataPackage.data;
      });
    }
  }

  goBack(): void {
    this.location.back();
  }

  save(): void {
    this.consultorioService.save(this.centroId!, this.consultorio).subscribe({
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