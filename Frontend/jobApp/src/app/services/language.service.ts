import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { User } from '../interfaces/interfaces';

@Injectable({
  providedIn: 'root'
})
export class LanguageService {

  baseUrl:string=`${environment.baseUrl}/language`;

  constructor(
    private http:HttpClient
  ) { }

  addLanguage(form:NgForm):Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/add`,form.value);
  }

  editLanguage(form:NgForm,id:number):Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/edit/${id}`,form.value);
  }

  deleteLanguage(id:number):Observable<User> {
    return this.http.delete<User>(`${this.baseUrl}/delete/${id}`);
  }

}
