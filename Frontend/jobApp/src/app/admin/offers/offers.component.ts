import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Category, Offer, User } from 'src/app/interfaces/interfaces';
import { CategoryService } from 'src/app/services/category.service';
import { OfferService } from 'src/app/services/offer.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-offers',
  templateUrl: './offers.component.html',
  styleUrls: ['./offers.component.css']
})
export class OffersComponent implements OnInit {

  offers: Offer[] = [];
  page: number = 1;
  @ViewChild('searchForm') searchForm!: NgForm;
  categories: Category[] = [];
  enterprises: User[] = [];

  constructor(
    private categoryService: CategoryService,
    private offerService: OfferService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    //Show all offers
    this.offerService.allOffers().subscribe((offers: Offer[]) => {
      if (offers) this.offers = offers;
    });
    //Show all categories
    this.categoryService.getAllCategoriesAdmin().subscribe((categories: Category[]) => {
      if (categories) this.categories = categories;
    });
    //Show all users
    this.userService.findAll().subscribe((users: User[]) => {
      if (users) {
        //Get only ENTERPRISE users
        this.enterprises = users.filter((us: User) => us.enterprise);
      }
    })
  }

  filterOffers() {
    if (this.searchForm.pristine) return;
    this.offerService.filterOffersAdmin(this.searchForm).subscribe((offers: Offer[]) => {
      if (offers) this.offers = offers;
    });
  }

  //Delete offer DEFINITELY
  deleteOffer(id: number) {
    this.offerService.deleteOfferDefinitely(id);
  }

}
