import { Component, OnInit } from '@angular/core';
import { Router, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginService } from '../login/login.service';

@Component({
    selector: 'app-panel-home',
    standalone: true,
    imports: [RouterModule, CommonModule],
    templateUrl: './panel-home.component.html',
    styleUrl: './panel-home.component.css'
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
        this.isAdmin = this.router.url.includes('/admin');
        this.prefix = this.isAdmin ? '/admin' : '/usuario';

        this.loginService.currentUser$.subscribe(user => {
            this.currentUser = user;
        });
    }

    logout(): void {
        this.loginService.logout();
        this.router.navigate(['/']);
    }
}