import { Component, DoCheck } from '@angular/core';
import { Router } from '@angular/router';

import { AuthService } from 'src/app/services/auth.service';

@Component({
  selector: 'app-header',
  templateUrl: './header.component.html',
  styleUrls: ['./header.component.css']
})
export class HeaderComponent implements DoCheck {
  //Show buttons depending on the user's role/authentication
  logged:boolean=false;
  showRegisterButton: boolean = false;
  showLoginButton: boolean = false;
  showLogoutButton: boolean = false;
  showProfileButton: boolean = false;
  showMyOffersButton: boolean = false;
  userName:string="";
  id:number=0;

  constructor(
    private authService: AuthService,
    private router:Router
  ) { }

  ngDoCheck(): void {
    this.logged=(sessionStorage.getItem('token')) ? true : false;
    this.showRegisterButton = !this.logged;
    this.showLoginButton = !this.logged;
    this.showLogoutButton = this.logged;
    this.showProfileButton = this.logged
    this.showMyOffersButton=this.logged;
    if(sessionStorage.getItem('user_id')) this.id=parseInt(sessionStorage.getItem('user_id')!);
  }

  logout() {
    this.authService.logout();
  }

}
