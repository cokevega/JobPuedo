import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Observable } from 'rxjs';
import { User } from '../interfaces/interfaces';

import { environment } from 'src/environments/environment';

@Injectable({
  providedIn: 'root'
})
export class SkillService {

  baseUrl:string=`${environment.baseUrl}/skill`;

  constructor(
    private http:HttpClient
  ) { }

  addSkill(form:NgForm):Observable<User> {
    return this.http.post<User>(`${this.baseUrl}/add`,form.value);
  }

  editSkill(form:NgForm,id:number):Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/edit/${id}`,form.value);
  }

  deleteSkill(id:number):Observable<User> {
    return this.http.delete<User>(`${this.baseUrl}/delete/${id}`);
  }

}
