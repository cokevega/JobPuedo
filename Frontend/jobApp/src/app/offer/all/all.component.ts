import { Component, DoCheck, OnInit } from '@angular/core';

import { Application, Offer, Role, User } from 'src/app/interfaces/interfaces';
import { OfferService } from 'src/app/services/offer.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-all',
  templateUrl: './all.component.html',
  styleUrls: ['./all.component.css']
})
export class AllComponent implements OnInit, DoCheck {

  offers: Offer[] = [];
  user!: User;
  page: number=1;
  isAdmin:boolean=false;

  constructor(
    private offerService: OfferService,
    private userService:UserService
  ) { }

  ngDoCheck(): void {
    //¿El usuario es administrador?
    this.isAdmin=((this.user?.roles.find((role:Role)=>role.name==="ROLE_ADMIN"))!==undefined);
  }

  ngOnInit(): void {
    //Rescatar mis ofertas
    this.offerService.myOffers().subscribe((offers:Offer[])=>{
      if(offers) {
        this.offers=offers;
        //Ordenar por fecha
        this.offers.sort((a:Offer,b:Offer)=>{
          if(a.date>b.date) return -1;
          else return 1;
        })
      }
    });
    //Obtener el usuario que está navegando
    this.userService.whoAmI().subscribe((user:User)=>{
        if(user) this.user=user;
      });
  }

  //Comparar si la candidatura a esta oferta tiene un status determinado
  candidacyStatus(offer: Offer,status:string):boolean {
    let application:Application;
    let correctStatus:boolean=false;
    this.user?.applications?.forEach((app:Application)=> {
      application=offer.applications?.find((appOffer:Application)=>appOffer.id===app.id)!;
      if(application) correctStatus=(application.status===status);
    });
    return correctStatus;
  }

}
