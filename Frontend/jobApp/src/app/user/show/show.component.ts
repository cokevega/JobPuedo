//Mostrar perfil
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { User } from 'src/app/interfaces/interfaces';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.css']
})
export class ShowComponent implements OnInit {

  user!:User;
  userShowed!:User;

  constructor(
    private userService:UserService,
    private route:ActivatedRoute
  ) { }

  ngOnInit(): void {
    //Rescatar info de quien accede
    if(sessionStorage.getItem('user_id')) {
      this.userService.findUser(parseInt(sessionStorage.getItem('user_id')!)).subscribe((user:User)=>{
        this.user=user;
      });
    }
    //Rescatar info del perfil al que se intenta acceder
    this.userService.findUser(this.route.snapshot.params.id).subscribe((user:User)=>{
      this.userShowed=user;
    })
  }

}
