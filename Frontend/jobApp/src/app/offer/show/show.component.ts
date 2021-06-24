import { Component, Input, OnInit, ViewChild, ViewEncapsulation } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Params, Router } from '@angular/router';

import { User, Offer } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { ApplicationService } from 'src/app/services/application.service';
import { OfferService } from 'src/app/services/offer.service';

@Component({
  selector: 'app-show',
  templateUrl: './show.component.html',
  styleUrls: ['./show.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class ShowComponent implements OnInit {
  
  //¿Es para inscribirse?
  @Input('apply') apply:boolean=false;
  //Rescatar formulario
  @ViewChild('form') form!:NgForm;
  offer!: Offer;
  enterprise!:User;
  id:number=0;

  constructor(
    private activeRoute: ActivatedRoute,
    private alertService:AlertService,
    private applicationService:ApplicationService,
    private offerService: OfferService,
    private router:Router
  ) {
    //Rescatar el id de la oferta
    this.activeRoute.params.subscribe((params: Params) => {
      this.id = params.id;
    });
  }

  ngOnInit() {
    //Rescatar información de la oferta
    this.offerService.findOfferById(this.id).subscribe((offer: Offer) => {
      if (offer) {
        //Si la oferta no está activa no se puede acceder a ella
        if(offer.status!=='Active') {
          this.alertService.fail("La oferta solicitada ya no se encuentra activa.").then((result)=>{
            this.router.navigate(['/main/index']);
          });
        } else {
          this.offer = offer;
          this.enterprise=offer.enterprise;
        }
      }
    });
  }

  //Inscribirse a la oferta
  applyOffer(offer_id:number) {
    let data:FormData=new FormData();
    data.append('comments',this.form.controls.comments.value);
    data.append('offer_id',offer_id.toString());
    this.applicationService.applyOffer(data);
  }

}
