//Página de registro, tanto trabajadores como empresas
import { Component, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-register',
  templateUrl: './register.component.html',
  styleUrls: ['./register.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class RegisterComponent {

  //Formulario trabajadores
  formUser: FormGroup = this.fb.group({
    name: ['', Validators.required],
    last_name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    born: ['', [Validators.required]],
    phone: [''],
    password: ['', [Validators.required, Validators.minLength(8)]],
    confirm: ['', Validators.required],
    enterprise: [false],
  });
  //Formulario empresas
  formEnterprise: FormGroup = this.fb.group({
    contact_name: ['', Validators.required],
    contact_last_name: ['', Validators.required],
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(8)]],
    confirm: ['', [Validators.required, Validators.minLength(8)]],
    name: ['', Validators.required],
    cif: ['', [Validators.required, Validators.pattern(/^[A-Z]\d{8}$/)]],
    description: ['Introduce aquí una presentación de tu empresa para que puedan leer tus futuros trabajadores.'],
    enterprise: [true],
  });
  //Formulario a enviar
  form!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService
  ) { }
  
  //Registrar usuario, se escoge formulario en función del campo oculto enterprise
  register(enterprise: boolean) {
    this.form = (enterprise) ? this.formEnterprise : this.formUser;
    this.authService.register(this.form);
  }

  //Validar campo formulario trabajadores
  validateField(field: string) {
    if (field !== 'confirm') return this.formUser.controls[field].invalid && this.formUser.controls[field].touched;
    else return this.formUser.controls[field].value !== this.formUser.controls['password'].value;
  }

  //Validar campo formulario empresas
  validateFieldEnterprise(field: string) {
    if (field !== 'confirm') return this.formEnterprise.controls[field].invalid && this.formEnterprise.controls[field].touched;
    else return this.formEnterprise.controls[field].value !== this.formEnterprise.controls['password'].value;
  }

}
