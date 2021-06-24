//Comprobar si es un usuario trabajador
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, RouterStateSnapshot, UrlSegment } from '@angular/router';

import { Observable } from 'rxjs';
import { catchError, tap } from 'rxjs/operators';

import { AuthService } from '../services/auth.service';
import { AlertService } from '../services/alert.service';

@Injectable({
  providedIn: 'root'
})
export class IsUserGuard implements CanActivate, CanLoad {
  constructor(
    private alertService: AlertService,
    private authService: AuthService
  ) { }
  canLoad(
    route: Route,
    segments: UrlSegment[]): boolean | Observable<boolean> {
    return this.authService.validateUser().pipe(
      tap((valid: boolean) => {
        if (valid) return valid;
        else return this.alertService.unauthorizedGuard("Inicia sesión como trabajador para poder acceder")
      }),
      catchError((_) => {
        return this.alertService.unauthorizedGuard("Inicia sesión como trabajador para poder acceder")
      })
    );
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise<boolean> | Observable<boolean> {
    return this.authService.validateUser().pipe(
      tap((valid: boolean) => {
        if (valid) return valid;
        else return this.alertService.unauthorizedGuard("Inicia sesión como trabajador para poder acceder")
      }),
      catchError((_) => {
        return this.alertService.unauthorizedGuard("Inicia sesión como trabajador para poder acceder")
      })
    );
  }
  validate(): boolean {
    this.authService.validateUser().subscribe((authorized: boolean) => {
      if (!authorized) return false;
      else return authorized;
    });
    return true;
  }
}
