import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, RouterModule } from '@angular/router';
import { CentroAtencionService } from '../centro/centro-atencion.service';
import { CommonModule } from '@angular/common';
import { CentroAtencion } from '../centro/centro-atencion';
import { DataPackage } from '../data-package';
import { ModalService } from '../modal/modal.service'; 

@Component({
  selector: 'app-centro-medicos',
  standalone: true,
  imports: [CommonModule, RouterModule],
  templateUrl: './centro-medicos.component.html',
  styleUrl: './centro-medicos.component.css'
})
export class CentroMedicosComponent implements OnInit {
  medicos: any[] = [];
  idCentro!: number;
  centroNombre: string = 'Cargando...';

  constructor(
    private route: ActivatedRoute,
    private centroService: CentroAtencionService,
    private modalService: ModalService 
  ) { }

  ngOnInit(): void {
    this.idCentro = Number(this.route.snapshot.paramMap.get('id'));
    this.getCentroNombre();
  }

  getCentroNombre(): void {
    if (this.idCentro) {
      this.centroService.get(this.idCentro.toString()).subscribe((dataPackage: DataPackage) => {
        const centro = <CentroAtencion>dataPackage.data;
        this.centroNombre = centro.nombre;
        this.cargarMedicos(this.centroNombre);
      });
    }
  }

  cargarMedicos(nombreCentro: string): void {
    this.centroService.obtenerMedicosDeCentro(nombreCentro).subscribe({
      next: (dataPackage: DataPackage) => {
        this.medicos = <any[]>dataPackage.data;
      },
      error: (err) => {
        console.error('Error al cargar los médicos:', err);
      }
    });
  }

  desasociar(medico: any): void {
    if (!medico.id) {
      this.modalService.confirm("Error", "No se encontró el ID del médico.", "");
      return;
    }
    this.modalService
      .confirm(
        "Desasociar Médico",
        `¿Está seguro de que desea desasociar al Dr./Dra. ${medico.apellido} de este centro?`,
        "Esta acción se puede revertir luego asociándolo nuevamente."
      )
      .then(() => {
        this.centroService.desasociarMedico(this.centroNombre, medico.id).subscribe({
          next: (dataPackage: DataPackage) => {
            this.cargarMedicos(this.centroNombre); 
            this.modalService.info("¡Éxito!", dataPackage.message, "");
          },
          error: (err) => {
            const errorResponse: DataPackage = err.error;
            this.modalService.confirm("Error:", errorResponse?.message || "Ocurrió un error inesperado", "");
          }
        });
      })
      .catch(() => {
      });
  }
}