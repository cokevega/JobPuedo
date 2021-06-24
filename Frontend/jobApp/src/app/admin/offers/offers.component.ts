//Panel del administrador para ofertas
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
  //Establecer primera página
  page: number = 1;
  //Recuperar formulario
  @ViewChild('searchForm') searchForm!: NgForm;
  categories: Category[] = [];
  enterprises: User[] = [];

  constructor(
    private categoryService: CategoryService,
    private offerService: OfferService,
    private userService: UserService
  ) { }

  ngOnInit(): void {
    //Rescatar todas las ofertas
    this.offerService.allOffers().subscribe((offers: Offer[]) => {
      if (offers) this.offers = offers;
    });
    //Rescatar todas las categorías
    this.categoryService.getAllCategoriesAdmin().subscribe((categories: Category[]) => {
      if (categories) this.categories = categories;
    });
    //Rescatar todos los usuarios
    this.userService.findAll().subscribe((users: User[]) => {
      if (users) {
        //Filtrar y obtener solo las empresas
        this.enterprises = users.filter((us: User) => us.enterprise);
      }
    })
  }

  //Filtrar ofertas
  filterOffers() {
    if (this.searchForm.pristine) return;
    this.offerService.filterOffersAdmin(this.searchForm).subscribe((offers: Offer[]) => {
      if (offers) this.offers = offers;
    });
  }

  //Eliminar oferta (definitivo)
  deleteOffer(id: number) {
    this.offerService.deleteOfferDefinitely(id);
  }

}
