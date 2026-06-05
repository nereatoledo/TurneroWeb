import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Turnero Web';
  rolActual: string = 'Seleccionar Entorno';
  dropdownOpen: boolean = false;

  constructor(private router: Router) {
    this.router.events.subscribe(event => {
      if (event instanceof NavigationEnd) {
        if (event.urlAfterRedirects.includes('/admin')) {
          this.rolActual = 'Administrador';
        } else if (event.urlAfterRedirects.includes('/usuario')) {
          this.rolActual = 'Usuario';
        } else if (event.urlAfterRedirects === '/') {
          this.rolActual = 'Seleccionar Entorno';
        }
      }
    });
  }

  ngOnInit(): void {}

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

cambiarRol(nuevoRol: string) {
    this.dropdownOpen = false; 
    let urlActual = this.router.url;

    if (nuevoRol === 'Administrador') {
      if (urlActual.includes('/usuario')) {
        urlActual = urlActual.replace('/usuario', '/admin');
      } else if (!urlActual.includes('/admin')) {
        urlActual = '/admin';
      }
    } else if (nuevoRol === 'Usuario') {
      if (urlActual.includes('/admin')) {
        urlActual = urlActual.replace('/admin', '/usuario');
      } else if (!urlActual.includes('/usuario')) {
        urlActual = '/usuario';
      }
    }

    this.router.navigateByUrl(urlActual, { replaceUrl: true });
  }
}