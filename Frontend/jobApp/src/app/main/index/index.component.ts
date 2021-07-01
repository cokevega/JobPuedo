import { Component, OnInit } from '@angular/core';

import { Offer } from 'src/app/interfaces/interfaces';
import { OfferService } from 'src/app/services/offer.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {

  offers: Offer[] = [];
  page: number = 1;

  constructor(
    private offerService: OfferService
  ) { }

  ngOnInit(): void {
    //Get all active offers
    this.offerService.getOffersByStatus("Active").subscribe((offers: Offer[]) => {
      this.offers = offers;
    });
  }

  //Show filtered offers
  showOffersSearched($event: Offer[]) {
    this.offers = $event;
  }

}
