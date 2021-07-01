import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Category } from 'src/app/interfaces/interfaces';
import { CategoryService } from 'src/app/services/category.service';

@Component({
  selector: 'app-categories',
  templateUrl: './categories.component.html',
  styleUrls: ['./categories.component.css']
})
export class CategoriesComponent implements OnInit {

  categories: Category[] = [];
  page: number = 1;
  @ViewChild('addForm') addForm!: NgForm;
  @ViewChild('editForm') editForm!: NgForm;

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    //Show all categories
    this.categoryService.getAllCategoriesAdmin().subscribe((categories: Category[]) => {
      if (categories) this.categories = categories;
    });
  }

  validateField(form: NgForm, field: string): boolean {
    return form?.controls[field]?.touched && form?.controls[field]?.invalid;
  }

  addCategory() {
    this.categoryService.addCategory(this.addForm);
  }

  editCategory(categoryEdit: Category) {
    this.categoryService.editCategory(categoryEdit);
  }

  deleteCategory(id: number) {
    this.categoryService.deleteCategory(id);
  }

  activateCategory(id:number) {
    this.categoryService.reactivateCategory(id);
  }

}
