import { Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Offer, User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { OfferService } from 'src/app/services/offer.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-offer-card',
  templateUrl: './offer-card.component.html',
  styleUrls: ['./offer-card.component.css']
})
export class OfferCardComponent implements OnInit {

  @Input('offer') offer!: Offer;
  @Input('complete') complete: boolean = false;
  @Input('apply') apply: boolean = false;
  rejectedOffers: string[] = [];
  id: number = 0;
  user!: User;
  baseUrl: string = environment.baseUrl;

  constructor(
    private alertService: AlertService,
    private offerService: OfferService,
    private route: ActivatedRoute,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    //Get the user, if authenticated
    if (sessionStorage.getItem('token')) {
      this.userService.whoAmI().subscribe((user: User) => {
        if (user) this.user = user;
      });
    }
  }

  //Reject offer: store it on local storage
  reject(id: number): void {
    this.alertService.confirmAction(
      'No volveremos a mostrarte esta oferta, siempre y cuando te conectes desde el mismo dispositivo y mismo navegador',
      'Sí, elimínala',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.offerService.rejectOffer(id);
      }
    });
  }

  showThisOffer(id: number): boolean {
    return this.offerService.showThisOffer(id);
  }

  //Delete offer (soft delete)
  deleteOffer(id: number) {
    this.alertService.confirmAction(
      'Esta acción es irreversible, no podrás seguir con el proceso de selección en esta oferta',
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.offerService.deleteOffer(id).subscribe((offer: Offer) => {
          if (offer) {
            this.offer = offer;
            this.alertService.successWithTitle('Oferta eliminada', 'Damos por concluido el proceso de selección en ella');
          }
        });
      }
    });
  }

  activeOffer(id: number) {
    this.alertService.confirmAction(
      'A partir de este momento, esta oferta se hará pública y los usuarios podrán verla e inscribirse',
      'Sí, activar'
    ).then((result) => {
      if (result.isConfirmed) {
        this.offerService.activeOffer(id).subscribe((offer: Offer) => {
          if (offer) {
            this.offer = offer;
            this.alertService.success("Has activado la oferta, damos por empezado el proceso de selección en ella");
          }
        });
      }
    });
  }

  showApplyButton(offer: Offer): boolean {
    let show: boolean = true;
    if (this.user) show = this.offerService.showApplyButton(offer, this.user);
    return show;
  }

  showRejectButton(): boolean {
    //Only reject offers at main page (/main/index)
    return this.route.snapshot.url[0].path === "index";
  }

}
