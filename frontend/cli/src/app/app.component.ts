import { Component, signal } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterOutlet, RouterModule],
  template: `
    <header class="navbar-container shadow-sm mb-4">
      <div class="container-fluid px-md-5 d-flex flex-column flex-md-row align-items-center py-2 px-3">
        
        <div class="d-flex align-items-center mr-md-auto">
          <a routerLink="/" class="home-icon-button mr-3" title="Inicio">
            <svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" fill="currentColor" viewBox="0 0 16 16">
              <path d="M8.707 1.5a1 1 0 0 0-1.414 0L.646 8.146a.5.5 0 0 0 .708.708L8 2.207l6.646 6.647a.5.5 0 0 0 .708-.708L13 5.793V2.5a.5.5 0 0 0-.5-.5h-1a.5.5 0 0 0-.5.5v1.293L8.707 1.5Z"/>
              <path d="m8 3.293 6 6V13.5a1.5 1.5 0 0 1-1.5 1.5h-9A1.5 1.5 0 0 1 2 13.5V9.293l6-6Z"/>
            </svg>
          </a>
          
          <span class="brand-label">Turnero Web</span>
        </div>
        
        <nav class="my-2 my-md-0">
          <a class="p-2 nav-link-custom" routerLink="/centros_atencion">
            Centros de Atención
          </a>
        </nav>
      </div>
    </header>

    <main class="container">
      <router-outlet></router-outlet>
    </main>
  `,
  styles: [`
    .navbar-container {
      background-color: #ffffff;
      border-bottom: 2px solid #8923dc; 
    }

    /* Botón solo con el ícono de casa, sin bordes */
    .home-icon-button {
      color: #8923dc;
      display: flex;
      align-items: center;
      justify-content: center;
      transition: transform 0.2s ease, color 0.2s ease;
      text-decoration: none;
    }

    /* Al pasar el mouse, se oscurece un poco y crece levemente (efecto pop) */
    .home-icon-button:hover {
      color: #6c1ab3; /* Un morado un poco más oscuro */
      transform: scale(1.15); /* Aumenta su tamaño un 15% */
    }

    /* Estilo para el nombre de la app */
    .brand-label {
      color: #000000;
      font-family: 'Hackensack', 'Roboto Slab', serif;
      font-size: 1.25rem;
      font-weight: bold;
      letter-spacing: 0.5px;
    }

    .nav-link-custom {
      color: #000000;
      font-family: 'Hackensack', 'Roboto Slab', serif;
      text-decoration: none;
      font-weight: 500;
      transition: color 0.3s ease;
      position: relative;
    }

    .nav-link-custom:hover {
      color: #8923dc;
    }

    .nav-link-custom::after {
      content: '';
      position: absolute;
      width: 0;
      height: 2px;
      display: block;
      margin-top: 2px;
      right: 0;
      background: #8923dc;
      transition: width 0.3s ease;
    }

    .nav-link-custom:hover::after {
      width: 100%;
      left: 0;
    }
  `],
})
export class AppComponent {
  protected readonly title = signal('Turnero Web');
}