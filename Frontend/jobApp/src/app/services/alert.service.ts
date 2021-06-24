import { Injectable } from '@angular/core';
import { Router } from '@angular/router';

import Swal, { SweetAlertResult } from 'sweetalert2';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class AlertService {

  constructor(
    private router: Router
  ) { }

  //Alerta de éxito
  successWithTitle(title: string, text: string):Promise<SweetAlertResult<any>> {
    return Swal.fire({
      position: 'center',
      icon: 'success',
      title,
      text,
      showConfirmButton: true,
      timer: 5000,
    });
  }

  //Alerta de éxito
  success(message: string):Promise<SweetAlertResult<any>> {
    return Swal.fire({
      position: 'center',
      icon: 'success',
      title: message,
      showConfirmButton: true,
      timer: 5000
    });
  }

  //Alerta de error
  fail(message: string):Promise<SweetAlertResult<any>> {
    return Swal.fire({
      position: 'center',
      icon: 'error',
      title: message,
      showConfirmButton: true,
      timer: 5000
    });
  }

  //Alerta de confirmación de la acción que va a realizarse
  confirmAction(text: string = '', confirmButtonText: string = 'Sí, seguro',
    cancelButtonColor: string = environment.redButton, confirmButtonColor: string = environment.blueButton)
    : Promise<SweetAlertResult<any>> {
    return Swal.fire({
      title: '¿Estás seguro?',
      text,
      icon: 'warning',
      showCancelButton: true,
      cancelButtonColor,
      cancelButtonText: 'Cancelar',
      confirmButtonColor,
      confirmButtonText
    });
  }

  //Alerta de acceso denegado
  unauthorizedGuard(message: string): Promise<boolean> {
    this.fail(message);
    return this.router.navigate(["/main/index"]);
  }
}
