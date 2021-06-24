//Componente de presentación de una oferta
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

  //Recibir oferta
  @Input('offer') offer!: Offer;
  //Recibir si es necesario mostrarla completa
  @Input('complete') complete: boolean = false;
  //Recibir si es para inscribirse en ella
  @Input('apply') apply: boolean = false;
  //Ofertas descartadas previamente
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
    //Si hay sesión iniciada, se rescata el usuario
    if (sessionStorage.getItem('token')) {
      this.userService.whoAmI().subscribe((user: User) => {
        if (user) this.user = user;
      });
    }
  }

  //Descartar oferta: se usa el localStorage del navegador
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

  //¿Mostrar esta oferta?
  showThisOffer(id: number): boolean {
    return this.offerService.showThisOffer(id);
  }

  //Eliminar oferta (soft delete)
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

  //Activar oferta
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

  //Mostrar botón de inscribirse a esta oferta
  showApplyButton(offer: Offer): boolean {
    let show: boolean = true;
    if (this.user) show = this.offerService.showApplyButton(offer, this.user);
    return show;
  }

  //Mostrar botón de descartar oferta
  showRejectButton(): boolean {
    return this.route.snapshot.url[0].path === "index";
  }

}
