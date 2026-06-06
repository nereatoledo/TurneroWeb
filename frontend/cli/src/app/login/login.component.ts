import { Component } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { Router, RouterModule } from '@angular/router'; // <-- CRUCIAL: Importar RouterModule
import { LoginService } from './login.service';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [CommonModule, FormsModule, RouterModule], // <-- CRUCIAL: Agregar RouterModule aquí
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {
  username: string = '';
  errorMessage: string = '';

  constructor(
    private loginService: LoginService, 
    private router: Router
  ) {}

  onSubmit() {
    const user = this.username.trim();
    this.errorMessage = ''; 

    if (user) {
      this.loginService.login(user).subscribe({
        next: (res) => {
          localStorage.setItem('currentUser', res.username);
          if (res.id) localStorage.setItem('currentUserId', res.id.toString());
          this.router.navigate(['/usuario']);
        },
        error: (err) => {
          this.errorMessage = 'Paciente no encontrado. Por favor, verifique el usuario.';
        }
      });
    }
  }

  ingresoLibreAdmin() {
    this.router.navigate(['/admin']);
  }
}