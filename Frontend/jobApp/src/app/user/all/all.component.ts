//Buscador de trabajadores
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { User } from 'src/app/interfaces/interfaces';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.css']
})
export class AllComponent implements OnInit {

  users:User[]=[];
  baseUrl:string=environment.baseUrl;
  page:number=1;
  //Rescatar formulario
  @ViewChild('searchForm') form!:NgForm;

  constructor(
    private userService:UserService
  ) { }

  ngOnInit(): void {
    //Rescatar usuarios activos trabajadores y ordenar sus experiencias y formaciones por fecha
    this.userService.findByStatus(1).subscribe((users:User[])=>{
        this.users=users;
        this.users.forEach((user:User)=>{
          if(user.experiences) this.userService.sortArrayByEnd(user.experiences);
          if(user.education) this.userService.sortArrayByEnd(user.education);
        });
      } 
    );
  }

  //Filtrar trabajadores
  filterWorkers() {
    this.userService.filterWorkers(this.form).subscribe((users:User[])=>{
      if(users) {
        this.users=users;
        this.users.forEach((user:User)=>{
          if(user.experiences) this.userService.sortArrayByEnd(user.experiences);
          if(user.education) this.userService.sortArrayByEnd(user.education);
        });
      }
    })
  }

}
