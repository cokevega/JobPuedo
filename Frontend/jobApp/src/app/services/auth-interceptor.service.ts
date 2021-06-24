//Clase que intercepta todas las peticiones entrantes y salientes para centralizar actuaciones
import { HttpErrorResponse, HttpEvent, HttpHandler, HttpInterceptor, HttpRequest, HttpResponse } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import { Observable, of } from 'rxjs';
import { catchError } from 'rxjs/operators';

import { AlertService } from './alert.service';
import { AuthService } from './auth.service';

import Swal from 'sweetalert2';
import { User } from '../interfaces/interfaces';

@Injectable({
  providedIn: 'root'
})
export class AuthInterceptorService implements HttpInterceptor {

  token!:string;

  constructor(
    private alertService:AlertService,
    private authService:AuthService,
    private router:Router
  ) { }

  intercept(req: HttpRequest<any>, next: HttpHandler): Observable<HttpEvent<any>> {
    if(sessionStorage.getItem('token')) this.token=sessionStorage.getItem('token')!;
    if(!this.token) return this.handleRequest(next.handle(req));
    const headers=req.clone({
      headers: req.headers.set('Authorization',`Bearer ${this.token}`)
    });
    return this.handleRequest(next.handle(headers));
  }

  handleRequest(request:Observable<HttpEvent<any>>):Observable<HttpEvent<any>> {
    return request.pipe(
      catchError((err:any)=>{
        if(err instanceof HttpErrorResponse) {
          let handled:boolean=false;
          switch (err.status) {
            case 400:
              if(err.error.exception==="NoExistsResourceException")  {
                this.alertService.fail("El contenido solicitado no existe.");
                handled=true;
                this.router.navigate(['/main/index']);
              } else if(
                  err.error.exception==="ConstraintViolationException" ||
                  err.error.exception==="TransactionSystemException" || 
                  err.error.exception==="HttpMessageNotReadableException"
                ) {
                this.alertService.fail("Asegúrate de completar los campos obligatorios, con el formato especificado, en caso de ser requerido.");
                handled=true;
              } else if(
                err.error.exception==="BadCredentialsException"
              ) {
                this.alertService.fail("Email o contraseña inválidos");
                handled=true;
              }
              break;
            case 401:
              this.alertService.fail("Acceso denegado. Asegúrate de iniciar sesión y tener acceso a este recurso.");
              handled=true;
              this.router.navigate(['/auth/login']);
              break;
            case 423:
              Swal.fire({
                title: 'Cuenta bloqueada',
                text: "Esta cuenta se encuentra inactiva. ¿Deseas volver a activarla?",
                icon: 'warning',
                showCancelButton: true,
                confirmButtonColor: '#3085d6',
                cancelButtonColor: '#d33',
                cancelButtonText: 'Cancelar',
                confirmButtonText: 'Sí, activar'
              }).then((result) => {
                if (result.isConfirmed) {
                  if(err.error.id) {
                    this.authService.reactivateAccount(err.error.id).subscribe((user:User)=>{
                      if(user) this.alertService.success("Cuenta reactivada. Ya puedes iniciar sesión con ella.");
                    });
                  }
                }
              });
              handled=true;
              break;
            default:
              
              handled=true;
              break;
          }
          if(!handled) {
            console.log(err);
            this.alertService.fail(err.error.message);
          }
        }
        return of(err);
      })
    );
  }

}
