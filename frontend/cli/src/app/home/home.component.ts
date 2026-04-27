import { Component } from '@angular/core';
import { RouterModule } from '@angular/router';

@Component({
  selector: 'app-home',
  standalone: true,
  imports: [RouterModule],
  template: ` 
    <div class="container py-5 mt-md-4 text-center header-animation"> 
      
      <h1 class="display-4 custom-text mb-4">Bienvenido al Turnero Web de Centros de atención</h1>
      <p class="lead custom-text mb-5 text-muted">Sistema centralizado para la gestión de turnos.</p>
      
      <div class="row justify-content-center mt-5 px-3">
        
        <div class="col-md-5 mb-4">
          <a routerLink="/centros_atencion" class="action-card">
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                <path fill-rule="evenodd" d="M2 2.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5V3a.5.5 0 0 0-.5-.5H2zM3 3H2v1h1V3z"/>
                <path d="M5 3.5a.5.5 0 0 1 .5-.5h9a.5.5 0 0 1 0 1h-9a.5.5 0 0 1-.5-.5zM5.5 7a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1h-9zm0 4a.5.5 0 0 0 0 1h9a.5.5 0 0 0 0-1h-9z"/>
                <path fill-rule="evenodd" d="M1.5 7a.5.5 0 0 1 .5-.5h1a.5.5 0 0 1 .5.5v1a.5.5 0 0 1-.5.5H2a.5.5 0 0 1-.5-.5V7zM2 7h1v1H2V7zm0 3.5a.5.5 0 0 0-.5.5v1a.5.5 0 0 0 .5.5h1a.5.5 0 0 0 .5-.5v-1a.5.5 0 0 0-.5-.5H2zm1 .5H2v1h1v-1z"/>
              </svg>
            </div>
            <h3 class="custom-text h4 mb-3">Lista de Centros disponibles</h3>
            <p class="description-text">Consulta, edita o elimina los centros de atención que ya están registrados en el sistema.</p>
          </a>
        </div>

        <div class="col-md-5 mb-4">
          <a routerLink="/centros_atencion/new" class="action-card card-delay">
            <div class="card-icon">
              <svg xmlns="http://www.w3.org/2000/svg" fill="currentColor" viewBox="0 0 16 16">
                <path d="M14.763.075A.5.5 0 0 1 15 .5v15a.5.5 0 0 1-.5.5h-3a.5.5 0 0 1-.5-.5V14h-1v1.5a.5.5 0 0 1-.5.5h-9a.5.5 0 0 1-.5-.5V10a.5.5 0 0 1 .342-.474L6 7.64V4.5a.5.5 0 0 1 .276-.447l8-4a.5.5 0 0 1 .487.022zM6 8.694 1 10.36V15h5V8.694zM7 15h2v-1.5a.5.5 0 0 1 .5-.5h2a.5.5 0 0 1 .5.5V15h2V1.309l-7 3.5V15z"/>
                <path d="M9 5.5a.5.5 0 0 1 .5-.5h1V4a.5.5 0 0 1 1 0v1h1a.5.5 0 0 1 0 1h-1v1a.5.5 0 0 1-1 0v-1h-1a.5.5 0 0 1-.5-.5z"/> 
              </svg>
            </div>
            <h3 class="custom-text h4 mb-3">Crear un Nuevo Centro</h3>
            <p class="description-text">Agrega un nuevo centro de atención especificando su ubicación y datos de contacto.</p>
          </a>
        </div>

      </div>
    </div>
    `,
  styles: [`
    /* Tipografía Hackensack y color negro */
    .custom-text {
      color: #000000;
      font-family: 'Hackensack', 'Roboto Slab', serif; 
    }

    .description-text {
      color: #6c757d;
      font-size: 0.95rem;
      line-height: 1.5;
    }

    /* --- Animación de Entrada (Deslizar hacia arriba) --- */
    .header-animation {
      animation: slideUpFade 0.8s ease-out forwards;
    }

    /* --- Estructura de las Tarjetas --- */
    .action-card {
      display: block;
      background-color: #f8f9fa; /* Un blanco atenuado/gris muy claro para descansar la vista */
      border: 1.5px solid #e9ecef;
      border-radius: 16px;
      padding: 40px 30px;
      text-decoration: none;
      height: 100%;
      
      /* Animación inicial y transición para el hover */
      opacity: 0;
      transform: translateY(40px);
      animation: slideUpFade 0.6s ease-out forwards;
      /* La función cubic-bezier es el secreto para el "rebote suave" */
      transition: all 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275); 
    }

    /* Retrasamos la segunda tarjeta para que entren en cascada */
    .card-delay {
      animation-delay: 0.2s;
    }

    /* --- Efecto Hover (Rebote suave) --- */
    .action-card:hover {
      background-color: #ffffff; /* Se aclara un poquito al enfocarla */
      border-color: #8923dc;
      transform: translateY(-12px) scale(1.02); /* Sube y se agranda apenas */
      box-shadow: 0 15px 30px rgba(137, 35, 220, 0.12); /* Sombra morada difuminada */
      text-decoration: none;
    }

    /* --- Estilo de los Íconos --- */
    .card-icon {
      color: #8923dc;
      margin-bottom: 24px;
    }

    .card-icon svg {
      width: 65px;
      height: 65px;
      transition: transform 0.4s cubic-bezier(0.175, 0.885, 0.32, 1.275);
    }

    /* El ícono también reacciona cuando pasás el mouse por la tarjeta */
    .action-card:hover .card-icon svg {
      transform: scale(1.15) rotate(-3deg);
    }

    /* Keyframes para la aparición de los elementos */
    @keyframes slideUpFade {
      0% {
        opacity: 0;
        transform: translateY(40px);
      }
      100% {
        opacity: 1;
        transform: translateY(0);
      }
    }
  `]
})
export class HomeComponent {}