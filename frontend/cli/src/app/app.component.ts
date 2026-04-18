import { Component } from '@angular/core';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { CentroAtencion } from './centro-atencion';
import { CentroAtencionService } from './centro-atencion.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [FormsModule, CommonModule],

  template: `
    <div style="padding: 20px; max-width: 400px; font-family: sans-serif;">
      <h2>Crear Centro de Atención</h2>
      
      <div *ngIf="mensaje" [style.color]="error ? 'red' : 'green'" style="margin-bottom: 10px; font-weight: bold;">
        {{ mensaje }}
      </div>

      <form (ngSubmit)="guardar()">
        <div style="margin-bottom: 10px;">
            <input [(ngModel)]="nuevoCentro.nombre" name="nombre" placeholder="Nombre" required style="width: 100%; padding: 8px;">
        </div>
        <div style="margin-bottom: 10px;">
            <input [(ngModel)]="nuevoCentro.direccion" name="direccion" placeholder="Dirección" required style="width: 100%; padding: 8px;">
        </div>
        <div style="margin-bottom: 10px;">
            <input [(ngModel)]="nuevoCentro.localidad" name="localidad" placeholder="Localidad" style="width: 100%; padding: 8px;">
        </div>
        <div style="margin-bottom: 10px;">
            <input [(ngModel)]="nuevoCentro.provincia" name="provincia" placeholder="Provincia" style="width: 100%; padding: 8px;">
        </div>
        <div style="margin-bottom: 10px;">
            <input [(ngModel)]="nuevoCentro.coordenadas" name="coordenadas" placeholder="Coordenadas (ej: -42.7, -65.0)" style="width: 100%; padding: 8px;">
        </div>
        
        <button type="submit" style="padding: 10px 15px; cursor: pointer;">Guardar Centro</button>
      </form>
    </div>
  `
})
export class AppComponent {
  // Creamos el objeto vacío
  nuevoCentro: CentroAtencion = {
    nombre: '',
    direccion: '',
    localidad: '',
    provincia: '',
    coordenadas: ''
  };

  mensaje: string = '';
  error: boolean = false;

  constructor(private service: CentroAtencionService) {}

  guardar() {
    this.service.crear(this.nuevoCentro).subscribe({

      next: (res: any) => {
        this.mensaje = "¡Éxito! " + res.message;
        this.error = false;
        this.nuevoCentro = { nombre: '', direccion: '', localidad: '', provincia: '', coordenadas: '' };
      },
      
      error: (err: any) => {
        this.mensaje = "ERROR. " + err.error.message;
        this.error = true;
      }
    });
  }
}