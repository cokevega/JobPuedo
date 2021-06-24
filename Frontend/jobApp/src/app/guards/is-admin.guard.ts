//Comprobar si es administrador
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, Router, RouterStateSnapshot, UrlSegment } from '@angular/router';

import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';
import { AlertService } from '../services/alert.service';

@Injectable({
  providedIn: 'root'
})
export class IsAdminGuard implements CanActivate, CanLoad {
  constructor(
    private authService: AuthService,
    private router: Router,
    private alertService: AlertService
  ) { }
  canLoad(
    route: Route,
    segments: UrlSegment[]): Promise<boolean> | Observable<boolean> {
      return this.authService.validateAdmin();
  }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> {
      return this.authService.validateAdmin();
  }

}
