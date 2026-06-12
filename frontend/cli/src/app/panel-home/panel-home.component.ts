import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginService } from '../login/login.service';

@Component({
    selector: 'app-panel-home', // O el nombre de selector que estés usando para esta pantalla
    standalone: true,
    imports: [RouterModule, CommonModule],
    templateUrl: './panel-home.component.html', // Ajustá el nombre si es necesario
    styleUrl: './panel-home.component.css'      // Ajustá el nombre si es necesario
})
export class PanelHomeComponent implements OnInit {
    isAdmin: boolean = false;
    prefix: string = '/usuario';
    currentUser: any = null;

    constructor(
        private router: Router,
        private loginService: LoginService
    ) { }

    ngOnInit(): void {
        // Detectamos si estamos en la ruta de admin o de usuario
        this.isAdmin = this.router.url.includes('/admin');
        this.prefix = this.isAdmin ? '/admin' : '/usuario';

        // Obtenemos el usuario actual reactivamente
        this.loginService.currentUser$.subscribe(user => {
            this.currentUser = user;
        });
    }

    logout(): void {
        this.loginService.logout();
        this.router.navigate(['/']);
    }
}