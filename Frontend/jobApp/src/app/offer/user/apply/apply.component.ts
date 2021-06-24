import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Offer } from 'src/app/interfaces/interfaces';
import { OfferService } from 'src/app/services/offer.service';

@Component({
  selector: 'app-apply',
  templateUrl: './apply.component.html',
  styleUrls: ['./apply.component.css']
})
export class ApplyComponent implements OnInit {

  offer!:Offer;

  constructor(
    private offerService:OfferService,
    private route:ActivatedRoute
  ) { }

  ngOnInit(): void {
    //Rescatar información de la oferta
    this.offerService.findOfferById(this.route.snapshot.params.id).subscribe((offer:Offer)=>{
      this.offer=offer;
    });
  }

}
