//Añadir formación
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { EducationService } from 'src/app/services/education.service';

@Component({
  selector: 'app-add-education',
  templateUrl: './add-education.component.html',
  styleUrls: ['./add-education.component.css']
})
export class AddEducationComponent {

  //Recibir usuario
  @Input('user') user!: User;
  //Rescatar formulario
  @ViewChild('formStudy') formStudy!: NgForm;
  //Emitir nuevo usuario
  @Output() emitUser=new EventEmitter<User>();

  constructor(
    private alertService: AlertService,
    private educationService: EducationService
  ) { }

  //Validar campo
  validateFieldStudy(field: string) {
    return this.formStudy?.controls[field]?.invalid && this.formStudy.controls[field].touched;
  }

  //Añadir formación
  addStudy() {
    this.educationService.addEducation(this.formStudy).subscribe((user:User)=>{
      if(user) {
        this.alertService.success("Estudios añadidos correctamente").then((result)=>{
          this.emitUser.emit(user);         
        });
      }
    });
  }

}
