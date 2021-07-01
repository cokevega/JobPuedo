import { Component, OnInit, ViewEncapsulation } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';

import { Category } from 'src/app/interfaces/interfaces';
import { CategoryService } from 'src/app/services/category.service';
import { OfferService } from 'src/app/services/offer.service';

@Component({
  selector: 'app-create',
  templateUrl: './create.component.html',
  styleUrls: ['./create.component.css'],
  encapsulation: ViewEncapsulation.None
})
export class CreateComponent implements OnInit {

  constructor(
    private categoryService: CategoryService,
    private fb: FormBuilder,
    private offerService:OfferService,
  ) { }

  check:boolean=true;
  categories: Category[] = [];
  form:FormGroup=this.fb.group({
    name: ['',Validators.required],
    salary: ['',[Validators.required,Validators.min(0)]],
    category: ['Selecciona categoría',[Validators.required,Validators.pattern(/\d+/)]],
    description: ['',[Validators.required,Validators.minLength(50)]],
    details: ['Describe aquí los detalles de la oferta.',[Validators.required,Validators.minLength(100)]],
    status: [false]
  });

  ngOnInit(): void {
    //Show all categories
    this.categoryService.getAllCategories().subscribe((categories: Category[]) => {
      this.categories = categories;
    });
  }

  validateField(field:string):boolean {
    return this.form.controls[field].invalid && this.form.controls[field].touched;
  }

  createOffer() {
    this.offerService.createOffer(this.form);
  }

}
