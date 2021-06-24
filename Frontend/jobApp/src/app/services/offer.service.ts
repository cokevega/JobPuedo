import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { FormGroup, NgForm } from '@angular/forms';
import { Params, Router } from '@angular/router';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Application, Offer, User } from '../interfaces/interfaces';
import { AlertService } from './alert.service';

@Injectable({
  providedIn: 'root'
})
export class OfferService {

  baseUrl: string = `${environment.baseUrl}/offer`;
  baseUrlAdmin: string = `${environment.baseUrl}/admin/offer`;
  headers: HttpHeaders = new HttpHeaders({
    'Content-type': 'application/json'
  });

  constructor(
    private alertService: AlertService,
    private http: HttpClient,
    private router: Router
  ) { }

  //Buscar por id
  findOfferById(id: number): Observable<Offer> {
    let params: Params = { id };
    return this.http.get<Offer>(`${this.baseUrl}/show`, { params });
  }

  //Buscar por status
  getOffersByStatus(status: string): Observable<Offer[]> {
    let params: Params = {
      status
    };
    return this.http.get<Offer[]>(`${this.baseUrl}/all/status`, {
      params
    });
  }

  //Filtrar oferta
  getOfferSearched(form: NgForm): Observable<Offer[]> {
    return this.http.post<Offer[]>(`${this.baseUrl}/search`, form.value);
  }

  //Crear oferta
  createOffer(form: FormGroup) {
    this.http.post<Offer>(`${this.baseUrl}/create`, JSON.stringify(form.value), {
      headers: this.headers
    }).subscribe((newOffer: Offer) => {
      if (newOffer) {
        this.alertService.success("Nueva oferta creada satisfactoriamente").then((result) => {
          this.router.navigate(['/offer/all']);
        });
      }
    });;
  }

  //Obtener mis ofertas (para la sección de dashboard)
  myOffers(): Observable<Offer[]> {
    return this.http.get<Offer[]>(`${this.baseUrl}/dashboard`);
  }

  //Editar oferta
  editOffer(offer: Offer) {
    this.http.put<Offer>(`${this.baseUrl}/edit`, offer).subscribe((offer: Offer) => {
      if (offer) {
        this.alertService.success("Oferta editada correctamente").then((result) => {
          this.router.navigate(['/offer/show', offer.id]);
        });
      }
    });
  }

  //Eliminar oferta (soft delete)
  deleteOffer(id: number): Observable<Offer> {
    let params: Params = { id };
    return this.http.delete<Offer>(`${this.baseUrl}/delete`, { params });
  }

  //Activar oferta
  activeOffer(id: number): Observable<Offer> {
    let params: Params = { id };
    return this.http.get<Offer>(`${this.baseUrl}/activate`, { params });
  }

  //Obtener todas las ofertas
  allOffers(): Observable<Offer[]> {
    return this.http.get<Offer[]>(`${this.baseUrlAdmin}/all`);
  }

  //Filtrar ofertas (admin)
  filterOffersAdmin(form: NgForm): Observable<Offer[]> {
    return this.http.post<Offer[]>(`${this.baseUrlAdmin}/filter`, form.value);
  }

  //Eliminar oferta (definitivo)
  deleteOfferDefinitely(id: number) {
    this.alertService.confirmAction(
      'Eliminar esta oferta supondrá la eliminación de todas sus inscripciones también. Esta acción no podrá deshacerse',
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.http.delete<boolean>(`${this.baseUrlAdmin}/delete/${id}`).subscribe((deleted: boolean) => {
          this.alertService.success("Oferta eliminada correctamente").then((result) => {
            window.location.reload();
          });
        });
      }
    })
  }

  //Descartar oferta
  rejectOffer(id: number) {
    let rejectedOffers: string[] = [];
    if (localStorage.getItem('rejectedOffers')) rejectedOffers = localStorage.getItem('rejectedOffers')?.split(',')!;
    let rejected = id.toString();
    rejectedOffers.push(rejected);
    localStorage.setItem('rejectedOffers', rejectedOffers.toString());
    this.alertService.successWithTitle("Descartada", "Has descartado la oferta, no volveremos a mostrártela");
  }

  //Mostrar esta oferta si no está descartada
  showThisOffer(id: number): boolean {
    let rejectedOffers: string[] = [];
    if (localStorage.getItem('rejectedOffers')) rejectedOffers = localStorage.getItem('rejectedOffers')?.split(',')!;
    let showOffer = (rejectedOffers.find((value) => value === id.toString()) === undefined);
    return showOffer;
  }

  //Mostrar botón de inscribirse a oferta
  showApplyButton(offer: Offer, user: User): boolean {
    let show: boolean = true;
    if (user.enterprise) show = false;
    else {
      user.applications?.forEach((appUser: Application) => {
        if (offer.applications?.find((appOffer: Application) => appOffer.id === appUser.id) !== undefined) show = false;
      });
    }
    return show;
  }

}
