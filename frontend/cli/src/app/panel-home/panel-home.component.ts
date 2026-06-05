import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';

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

    constructor(private router: Router) { }

    ngOnInit(): void {
        // Detectamos si estamos en la ruta de admin o de usuario
        this.isAdmin = this.router.url.includes('/admin');
        this.prefix = this.isAdmin ? '/admin' : '/usuario';
    }
}