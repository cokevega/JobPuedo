import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Experience, User } from '../interfaces/interfaces';

@Injectable({
  providedIn: 'root'
})
export class ExperienceService {

  baseUrl:string=`${environment.baseUrl}/experience`;

  constructor(
    private http: HttpClient
  ) { }

  //Añadir experiencia
  addExperience(data:NgForm): Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/add`,data.value);
  }

  //Editar experiencia
  editExperience(data:NgForm,id:number):Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/edit/${id}`,data.value);
  }

  //Eliminar experiencia
  deleteExperience(id:number):Observable<User> {
    return this.http.delete<User>(`${this.baseUrl}/delete/${id}`);
  }

}
