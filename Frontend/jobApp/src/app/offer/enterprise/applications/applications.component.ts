//Inscripciones a una oferta
import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';

import { Application } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { ApplicationService } from 'src/app/services/application.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-applications',
  templateUrl: './applications.component.html',
  styleUrls: ['./applications.component.css'],
})
export class ApplicationsComponent implements OnInit {

  applications:Application[]=[];
  page:number=1;

  constructor(
    private alertService:AlertService,
    private applicationService:ApplicationService,
    private route:ActivatedRoute
  ) { }

  ngOnInit(): void {
    //Recuperar inscripciones a la oferta
    this.applicationService.getApplicationsFromOffer(this.route.snapshot.params.id).subscribe((applications:Application[])=>{
      this.applications=applications.filter((app:Application)=>app.status!=='Rechazada');
    });
  }

  //Aceptar candidatura
  accept(application_id:number) {
    this.alertService.confirmAction(
      'Estás a punto de aceptar esta candidatura, este trabajador podrá ver que le has aceptado, así como tu email',
      'Sí, aceptar'
    ).then((result) => {
      if (result.isConfirmed) {
        this.applicationService.accept(application_id).subscribe((application:Application)=>{
          if(application) {
            this.alertService.success('Trabajador aceptado').then((result)=>{
              window.location.reload();
            });
          }
        });
      }
    });
  }

  //Rechazar candidatura
  reject(application_id:number) {
    this.alertService.confirmAction(
      'Estás a punto de rechazar a este trabajador y no volver a ver esta candidatura',
      'Sí, rechazar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.applicationService.reject(application_id).subscribe((application:Application)=>{
          if(application) {
            this.alertService.success('Has rechazado a este trabajador').then((result)=>{
              window.location.reload();
            })
          }
        });
      }
    });
  }

}
