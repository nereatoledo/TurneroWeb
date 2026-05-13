import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  template: ` 
    <div class="container py-5 mt-md-4 text-center"> 
      
      <h1 class="display-4 mb-4">
        Bienvenido al Turnero Web
      </h1>
      <p class="lead mb-5 text-muted">Sistema centralizado para la gestión de turnos.</p>
      
      <div class="row justify-content-center mt-5 px-3">
        
        <div class="col-md-4 mb-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body d-flex flex-column">
              <h5 class="card-title font-weight-bold mb-3">Centros de Atención</h5>
              <p class="text-muted small mb-4 flex-grow-1">
                Consulta, edita o elimina los centros de atención registrados en el sistema, y gestiona sus consultorios.
              </p>
              <a routerLink="/centros_atencion" class="btn btn-primary w-100 mb-2">
                <i class="fa fa-list mr-2"></i> Ver Centros
              </a>
              <a routerLink="/centros_atencion/new" class="btn btn-outline-success w-100">
                <i class="fa fa-plus mr-2"></i> Nuevo Centro
              </a>
            </div>
          </div>
        </div>

        <div class="col-md-4 mb-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body d-flex flex-column">
              <h5 class="card-title font-weight-bold mb-3">Staff Médico</h5>
              <p class="text-muted small mb-4 flex-grow-1">
                Administra la nómina de profesionales de la salud, sus datos personales y matrículas.
              </p>
              <a routerLink="/medicos" class="btn btn-info w-100 text-white mb-2">
                <i class="fa fa-user-md mr-2"></i> Lista de Médicos
              </a>
              <a routerLink="/medicos/new" class="btn btn-outline-info w-100">
                <i class="fa fa-plus mr-2"></i> Nuevo Médico
              </a>
            </div>
          </div>
        </div>

        <div class="col-md-4 mb-4">
          <div class="card h-100 shadow-sm">
            <div class="card-body d-flex flex-column">
              <h5 class="card-title font-weight-bold mb-3">Especialidades</h5>
              <p class="text-muted small mb-4 flex-grow-1">
                Gestiona el catálogo de especialidades médicas disponibles en el sistema.
              </p>
              <a routerLink="/especialidades" class="btn btn-secondary w-100 mb-2">
                <i class="fa fa-tags mr-2"></i> Ver Especialidades
              </a>
              <a routerLink="/especialidades/new" class="btn btn-outline-secondary w-100">
                <i class="fa fa-plus mr-2"></i> Nueva Especialidad
              </a>
            </div>
          </div>
        </div>

      </div>
    </div>
  `,
  styles: []
})
export class HomeComponent {}