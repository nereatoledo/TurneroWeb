import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router';
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule],
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  roleSelected: 'PACIENTE' | 'ADMIN' = 'PACIENTE';
  username: string = '';
  errorMessage: string = '';

  constructor(
    private loginService: LoginService,
    private router: Router
  ) { }

  onSubmit() {
    const user = this.username.trim();
    this.errorMessage = '';

    if (user) {
      this.loginService.login(user).subscribe({
        next: (res) => {
          if (res.rol === 'ADMIN') {
            if (this.roleSelected === 'PACIENTE') {
                this.errorMessage = 'El usuario ingresado es administrador. Seleccione el perfil correspondiente.';
                this.loginService.logout();
                return;
            }
            this.router.navigate(['/admin']);
          } else {
            if (this.roleSelected === 'ADMIN') {
                this.errorMessage = 'El usuario ingresado no es administrador.';
                this.loginService.logout();
                return;
            }
            this.router.navigate(['/usuario']);
          }
        },
        error: (err) => {
          this.errorMessage = 'Usuario no encontrado. Por favor, verifique sus datos.';
        }
      });
    }
  }
}