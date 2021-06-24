import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Category } from '../interfaces/interfaces';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root'
})
export class CategoryService {

  baseUrl: string = `${environment.baseUrl}/category`;
  baseUrlAdmin: string = `${environment.baseUrl}/admin/category`;

  constructor(
    private alertService: AlertService,
    private http: HttpClient
  ) { }

  //Obtener todas las categorías (activas)
  getAllCategories(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrl}/all`);
  }

  //Obtener todas las categorías (admin)
  getAllCategoriesAdmin(): Observable<Category[]> {
    return this.http.get<Category[]>(`${this.baseUrlAdmin}/all`);
  }

  //Añadir categoría
  addCategory(data: NgForm) {
    this.alertService.confirmAction(
      'Nada más añadir esta categoría los usuarios y empresas podrán verla y utilizarla',
      'Sí, añadir'
    ).then((result) => {
      if (result.isConfirmed) {
        this.http.post<Category>(`${this.baseUrlAdmin}/add`, data.value).subscribe((category: Category) => {
          if (category) {
            this.alertService.success("Categoría añadida correctamente").then((result) => {
              window.location.reload();
            });
          }
        });
      }
    });
  }

  //Editar categoría
  editCategory(category: Category) {
    this.http.put<Category>(`${this.baseUrlAdmin}/edit`, category).subscribe((category: Category) => {
      if (category) {
        this.alertService.success("Categoría editada correctamente").then((result) => {
          window.location.reload();
        });
      }
    });
  }

  //Eliminar categoría (soft delete)
  deleteCategory(id: number) {
    this.alertService.confirmAction(
      'Esta acción no podrá deshacerse y los usuarios dejarán de ver esta categoría, pero no se borrarán las ofertas que pertenecen a esta categoría',
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.http.delete<Category>(`${this.baseUrlAdmin}/delete/${id}`).subscribe((category: Category) => {
          if (category) {
            this.alertService.success("Categoría inactivada correctamente").then((result) => {
              window.location.reload();
            })
          }
        })
      }
    });
  }

  //Reactivar categoría inactiva
  reactivateCategory(id: number) {
    this.alertService.confirmAction(
      'Reactivar la categoría supondrá que los usuarios y las empresas puedan volver a verla y seleccionarla',
      'Sí, reactivar'
    ).then((result) => {
      if (result.isConfirmed) {
        this.http.get<Category>(`${this.baseUrlAdmin}/reactivate/${id}`).subscribe((category: Category) => {
          if (category) {
            this.alertService.success("Categoría reactivada correctamente").then((result) => {
              window.location.reload();
            });
          }
        });
      }
    })
  }

}
