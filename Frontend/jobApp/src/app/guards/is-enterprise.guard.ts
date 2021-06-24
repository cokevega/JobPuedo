//Comprobar si es un usuario empresa
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, CanLoad, Route, RouterStateSnapshot, UrlSegment } from '@angular/router';

import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class IsEnterpriseGuard implements CanActivate, CanLoad {
  constructor(
    private authService: AuthService
  ) { }
  canLoad(
    route: Route,
    segments: UrlSegment[]): Promise<boolean> | Observable<boolean> {
      return this.authService.validateEnterprise();
    }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Promise <boolean> | Observable<boolean> {
      return this.authService.validateEnterprise();
  }

}
