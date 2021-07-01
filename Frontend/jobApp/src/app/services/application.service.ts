import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Params, Router } from '@angular/router';

import { environment } from 'src/environments/environment';
import { Application } from '../interfaces/interfaces';
import { AlertService } from './alert.service';

import { Observable } from 'rxjs';

@Injectable({
  providedIn: 'root'
})
export class ApplicationService {

  baseUrl:string=`${environment.baseUrl}/application`;
  baseUrlAccess:string=`${environment.baseUrl}/accessibility`;

  constructor(
    private alertService:AlertService,
    private http:HttpClient,
    private router:Router
  ) { }

  applyOffer(data:FormData) {
    this.http.post<Application>(`${this.baseUrl}/create`,data).subscribe((application:Application)=>{
      if(application) {
        this.alertService.success("Te has inscrito a la oferta correctamente").then((result)=>{
          this.router.navigate(["/offer/all"]);
        });
      }
    });
  }

  checkOfferOwner(offer_id:number):Observable<boolean> {
    let params:Params={offer_id};
    return this.http.get<boolean>(`${this.baseUrlAccess}/offer/owner`,{params});
  }

  accept(application_id:number):Observable<Application> {
    let params:Params={application_id}
    return this.http.get<Application>(`${this.baseUrl}/accept`,{params});
  }

  reject(application_id:number):Observable<Application> {
    let params:Params={application_id}
    return this.http.get<Application>(`${this.baseUrl}/reject`,{params});
  }

  getMyApplications():Observable<Application[]> {
    return this.http.get<Application[]>(`${this.baseUrl}/dashboard`);
  }

  getApplicationsFromOffer(offer_id:number):Observable<Application[]> {
    let params:Params={offer_id};
    return this.http.get<Application[]>(`${this.baseUrl}/offer`,{params});
  }

}
