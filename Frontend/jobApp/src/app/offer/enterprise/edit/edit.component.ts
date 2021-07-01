import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute } from '@angular/router';

import { Category, Offer } from 'src/app/interfaces/interfaces';
import { CategoryService } from 'src/app/services/category.service';
import { OfferService } from 'src/app/services/offer.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  constructor(
    private categoryService: CategoryService,
    private offerService:OfferService,
    private route:ActivatedRoute,
  ) { }

  @ViewChild('form') form!:NgForm;
  categories: Category[] = [];
  offer!:Offer;
  id:number=this.route.snapshot.params.id;

  ngOnInit(): void {
    //All categories
    this.categoryService.getAllCategories().subscribe((categories: Category[]) => {
      this.categories = categories;
    });
    //Offer information
    this.offerService.findOfferById(this.id).subscribe((offer:Offer)=>{
      this.offer=offer;
    })
  }

  editOffer() {
    this.offerService.editOffer(this.offer);
  }

  validateField(field:string) {
    return this.form?.controls[field]?.invalid && this.form?.controls[field]?.touched;
  }

}
