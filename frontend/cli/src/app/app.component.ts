import { Component, OnInit } from '@angular/core';
import { Router, NavigationEnd, RouterModule } from '@angular/router';
import { CommonModule } from '@angular/common';
import { LoginService } from './home/login.service';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [RouterModule, CommonModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {
  title = 'Turnero Web';
  dropdownOpen: boolean = false;
  currentUser: any = null;

  constructor(private router: Router, private loginService: LoginService) {
    this.loginService.currentUser$.subscribe(user => {
      this.currentUser = user;
    });
  }

  ngOnInit(): void {}

  get isAdmin(): boolean {
    return this.loginService.isAdmin();
  }

  toggleDropdown() {
    this.dropdownOpen = !this.dropdownOpen;
  }

  logout() {
    this.loginService.logout();
    this.dropdownOpen = false;
    this.router.navigate(['/login']);
  }

  navigateToHome() {
    if (!this.currentUser) {
      this.router.navigate(['/login']);
    } else {
      if (this.loginService.isAdmin()) {
        this.router.navigate(['/admin']);
      } else {
        this.router.navigate(['/usuario']);
      }
    }
  }
}