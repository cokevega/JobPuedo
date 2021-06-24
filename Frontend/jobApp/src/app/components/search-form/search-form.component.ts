//Filtrar ofertas en el buscador del inicio
import { Component, EventEmitter, OnInit, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Category, Offer } from 'src/app/interfaces/interfaces';
import { CategoryService } from 'src/app/services/category.service';
import { OfferService } from 'src/app/services/offer.service';

@Component({
  selector: 'app-search-form',
  templateUrl: './search-form.component.html',
  styleUrls: ['./search-form.component.css']
})
export class SearchFormComponent implements OnInit {
  //Emitir ofertas filtradas
  @Output() offersEmitted = new EventEmitter<Offer[]>();
  //Rescatar formulario
  @ViewChild('searchForm') form!:NgForm;
  categories: Category[] = [];
  offers: Offer[] = [];
  tooltipMessage:string="";
  categoryDescription:Map<number,string>=new Map<number,string>();

  constructor(
    private categoryService: CategoryService,
    private offerService: OfferService,
  ) { }

  ngOnInit(): void {
    //Rescatar todas las categorías activas y cargar los tooltip con información de las categorías
    this.categoryService.getAllCategories().subscribe((categories: Category[]) => {
      if(categories) {
        this.categories = categories;
        this.categories.forEach((category:Category)=>{
          this.categoryDescription.set(category.id,category.description);
        });
      }
    });
  }

  //Filtrar ofertas
  searchOffers(form: NgForm):void {
    this.offerService.getOfferSearched(form).subscribe((offers:Offer[])=>{
      if(offers) this.handleSubscription(offers);
    });
  }

  //Resetear form y volver a mostrar todas
  resetOffers(form: NgForm):void {
    form.reset();
    this.offerService.getOffersByStatus("Active").subscribe((offers: Offer[]) => {
      if(offers) this.handleSubscription(offers);
    });
  }

  //Emitir ofertas filtradas
  handleSubscription(offers: Offer[]):void {
    this.offers = offers;
    this.offersEmitted.emit(this.offers);
  }

  //Cambiar tooltip
  changeTooltipMessage() {
    this.tooltipMessage=this.categoryDescription.get(parseInt(this.form.controls['category'].value))!;
  }

}
