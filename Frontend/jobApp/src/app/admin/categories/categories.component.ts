//Panel del administrador para trabajar con  las categorías
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
  //Establecer primera página
  page: number = 1;
  //Recuperar formularios
  @ViewChild('addForm') addForm!: NgForm;
  @ViewChild('editForm') editForm!: NgForm;

  constructor(
    private categoryService: CategoryService
  ) { }

  ngOnInit(): void {
    //Rescatar todas las categorías
    this.categoryService.getAllCategoriesAdmin().subscribe((categories: Category[]) => {
      if (categories) this.categories = categories;
    });
  }

  //Validar campo
  validateField(form: NgForm, field: string): boolean {
    return form?.controls[field]?.touched && form?.controls[field]?.invalid;
  }

  //Añadir categoría
  addCategory() {
    this.categoryService.addCategory(this.addForm);
  }

  //Editar categoría
  editCategory(categoryEdit: Category) {
    this.categoryService.editCategory(categoryEdit);
  }

  //Eliminar categoría
  deleteCategory(id: number) {
    this.categoryService.deleteCategory(id);
  }

  //Reactivar categoría inactiva
  activateCategory(id:number) {
    this.categoryService.reactivateCategory(id);
  }

}
