//Comprobar si está accediendo el dueño de la oferta
import { Injectable } from '@angular/core';
import { ActivatedRouteSnapshot, CanActivate, RouterStateSnapshot } from '@angular/router';

import { Observable } from 'rxjs';

import { ApplicationService } from '../services/application.service';

@Injectable({
  providedIn: 'root'
})
export class MyOfferGuard implements CanActivate {
  constructor(
    private applicationService: ApplicationService
  ) { }
  canActivate(
    route: ActivatedRouteSnapshot,
    state: RouterStateSnapshot): Observable<boolean> | Promise<boolean> {
    return this.applicationService.checkOfferOwner(route.params.id);
  }

}
