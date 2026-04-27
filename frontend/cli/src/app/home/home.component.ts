import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  template: ` 
    <div class="container py-5 mt-md-4 text-center"> 
      
      <h1 class="display-4 mb-4" style="color: #8923dc;">
        Bienvenido al Turnero Web de Centros de atención
      </h1>
      <p class="lead mb-5 text-muted">Sistema centralizado para la gestión de turnos.</p>
      
      <div class="row justify-content-center mt-5 px-3">
        
        <div class="col-md-5 mb-4">
          <a routerLink="/centros_atencion" class="btn btn-primary btn-lg w-100 mb-3">
            Lista de Centros disponibles
          </a>
          <p class="text-muted small">
            Consulta, edita o elimina los centros de atención que ya están registrados en el sistema.
          </p>
        </div>

        <div class="col-md-5 mb-4">
          <a routerLink="/centros_atencion/new" class="btn btn-success btn-lg w-100 mb-3">
            Crear un Nuevo Centro
          </a>
          <p class="text-muted small">
            Agrega un nuevo centro de atención especificando su ubicación y datos de contacto.
          </p>
        </div>

      </div>
    </div>
  `,
  styles: []
})
export class HomeComponent {}