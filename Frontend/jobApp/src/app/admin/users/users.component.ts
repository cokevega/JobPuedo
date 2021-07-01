import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Role, User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-users',
  templateUrl: './users.component.html',
  styleUrls: ['./users.component.css']
})
export class UsersComponent implements OnInit {

  users:User[]=[];
  page:number=1;
  baseUrl:string=environment.baseUrl;
  @ViewChild('searchForm') searchForm!:NgForm;

  constructor(
    private alertService:AlertService,
    private userService:UserService
  ) { }

  ngOnInit(): void {
    //Show all users
    this.userService.findAll().subscribe((users:User[])=>{
      if(users) this.users=users;
    });
  }

  newAdmin(id:number) {
    this.alertService.confirmAction(
      'Esta acción no podrá deshacerse y este usuario pasará a tener permisos de administrador para actuar sobre categorías, eliminar ofertas y usuarios y elegir nuevos administradores',
      'Sí, hacer admin'
    ).then((result)=>{
      if (result.isConfirmed) {
        this.userService.getNewAdmin(id).subscribe((user:User)=>{
          if(user) {
            this.alertService.success(`Se ha añadido a ${user.name} como administrador correctamente`).then((result)=>{
              window.location.reload();
            });
          }
        });
      }
    });
  }

  //Delete user DEFINITELY
  deleteUser(id:number) {
    this.alertService.confirmAction(
      'Serán eliminadas todas las inscripciones y ofertas de este usuario y no podrán recuperarse',
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result)=>{
      if (result.isConfirmed) {
        this.userService.deleteUserDefinitely(id).subscribe((deleted:boolean)=>{
          if(deleted) {
            this.alertService.success("Usuario eliminado correctamente").then((result)=>{
              window.location.reload();
            });
          }
        });
      }
    })
  }

  isAdmin(user:User):boolean {
    return user.roles.find((role:Role)=>role.name==='ROLE_ADMIN')!==undefined;
  }

  //Filter users
  searchUsers() {
    if(this.searchForm.pristine) return;
    this.userService.filterUser(this.searchForm).subscribe((users:User[])=>{
      if(users) this.users=users;
      else this.users=[];
    })
  }

}
