import { Component, Input } from '@angular/core';
import { Router } from '@angular/router';

import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-delete-account-button',
  templateUrl: './delete-account-button.component.html',
  styleUrls: ['./delete-account-button.component.css']
})
export class DeleteAccountButtonComponent {

  @Input('user') user!: User;

  constructor(
    private alertService:AlertService,
    private router:Router,
    private userService: UserService
  ) { }

  //Delete user (soft delete)
  deleteUser() {
    this.alertService.confirmAction(
      'Tu cuenta quedará inactiva a partir de este momento hasta que decidas reactivarla',
      'Sí, eliminar',environment.blueButton,environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.userService.deleteUser(this.user.id).subscribe((deleted: boolean) => {
          if (deleted === true) {
            //Logout
            sessionStorage.removeItem('token');
            this.alertService.successWithTitle('Eliminada','Tu cuenta ha sido inactivada correctamente').then((result)=>{
              this.router.navigate(['/auth/login']);
            });
          }
        });
      }
    })
  }

}
