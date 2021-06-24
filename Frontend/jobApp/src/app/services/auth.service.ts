import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup, NgForm } from '@angular/forms';
import { Params, Router } from '@angular/router';

import { environment } from 'src/environments/environment';
import { LoggedUser, LoginResponse, User } from '../interfaces/interfaces';
import { AlertService } from './alert.service';

import { Observable } from 'rxjs';


@Injectable({
  providedIn: 'root'
})
export class AuthService {

  private _user!: LoggedUser;
  get user() {
    return { ...this._user };
  }
  headers: HttpHeaders = new HttpHeaders({
    'Content-type': 'application/json'
  });
  baseUrl: string = `${environment.baseUrl}/auth`;
  baseUrlAccess: string = `${environment.baseUrl}/accessibility`;

  constructor(
    private http: HttpClient,
    private router: Router,
    private alertService: AlertService
  ) { }

  //Registrar nuevo usuario
  register(data: FormGroup) {
    return this.http.post<LoginResponse>(`${this.baseUrl}/register`, data.value).subscribe((data:LoginResponse)=>{
      if(data) this.loggedUser(data);
    });
  }

  //Iniciar sesión
  login(data: NgForm) {
    let options = { headers: this.headers };
    let formValue = JSON.stringify(data.value);
    this.http.post<LoginResponse>(`${this.baseUrl}/login`, formValue, options).subscribe((data: LoginResponse) => {
      if (data) this.loggedUser(data);
    });
  }

  //Si el login es correcto se almacenan los datos del usuario y se le redirige a su dashboard
  loggedUser(data:LoginResponse) {
    sessionStorage.setItem('token', data.accessToken);
    sessionStorage.setItem('user_id',data.id.toString());
    this.alertService.success('Sesión iniciada correctamente').then((result)=>{
      this.router.navigate(["/offer/all"]);
    });
  }

  //Cerrar sesión
  logout() {
    if (sessionStorage.getItem('token')) {
      sessionStorage.removeItem('token');
      sessionStorage.removeItem('user_id');
      this.alertService.success("Has cerrado sesión correctamente");
    }
    this.router.navigate(['/auth/login']);
  }

  //Guard enterprise
  validateEnterprise(): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrlAccess}/enterprise`);
  }

  //Guard user
  validateUser(): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrlAccess}/user`);
  }

  //Guard admin
  validateAdmin(): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrlAccess}/admin`);
  }

  //Guard identity
  checkIdentity(id: number): Observable<boolean> {
    const params: Params = { id };
    return this.http.get<boolean>(`${this.baseUrlAccess}/identity`, { params });
  }

  //Guard view profile
  viewProfile(id: number): Observable<boolean> {
    const params: Params = { id };
    return this.http.get<boolean>(`${this.baseUrlAccess}/profile`, { params });
  }

  //Obtener mi id
  getMyId(): Observable<number> {
    return this.http.get<number>(`${this.baseUrl}/id`);
  }

  validateUserEnterprise(): Observable<boolean> {
    return this.http.get<boolean>(`${this.baseUrlAccess}/authenticated`);
  }

  reactivateAccount(id: number): Observable<User> {
    let params: Params = { id };
    return this.http.get<User>(`${this.baseUrl}/reactivate`, { params });
  }

}
