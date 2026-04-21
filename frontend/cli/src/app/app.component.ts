import { Component, signal } from '@angular/core';
import { RouterModule, RouterOutlet } from '@angular/router';

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, RouterModule],
  template: `
    <div class="d-flex flex-column flex-md-row align-items-center p-3 bg-light border-bottom">
      <h5 class="my-0 mr-md-auto font-weight-normal">Turnero Web</h5>
      <nav class="my-2 mr-md-0 mr-md-3">
        <a class="p-2 text-dark" routerLink="/centro_atencion/new">Crear Centro</a>
      </nav>
    </div>
    <div class="container mt-4">
      <router-outlet></router-outlet>
    </div>
  `,
  styles: [],
})
export class AppComponent {
  protected readonly title = signal('Nere');
}