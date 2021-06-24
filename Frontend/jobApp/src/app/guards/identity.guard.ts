//Comprobar si accede el usuario dueño de la información a la que se quiere acceder
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot} from '@angular/router';

import { Observable } from 'rxjs';

import { AuthService } from '../services/auth.service';

@Injectable({
  providedIn: 'root'
})
export class IdentityGuard implements CanActivate {

  constructor(
    private authService:AuthService
  ) { }

  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> {
    return this.authService.checkIdentity(route.params.id);
  }
}
