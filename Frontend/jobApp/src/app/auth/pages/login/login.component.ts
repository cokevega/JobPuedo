//Página de login
import { Component } from '@angular/core';
import { NgForm } from '@angular/forms';

import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  constructor(
    private authService:AuthService
  ) { }

  //Iniciar sesión
  login(form:NgForm) {
    this.authService.login(form);
  }

}
