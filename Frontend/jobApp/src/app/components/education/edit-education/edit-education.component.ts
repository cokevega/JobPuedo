//Editar formación
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Education, User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { EducationService } from 'src/app/services/education.service';

@Component({
  selector: 'app-edit-education',
  templateUrl: './edit-education.component.html',
  styleUrls: ['./edit-education.component.css']
})
export class EditEducationComponent {

  //Recibir usuario
  @Input('user') user!:User;
  //Recibir formación
  @Input('study') study!:Education;
  //Emitir nuevo usuario
  @Output() emitUser=new EventEmitter<User>();
  //Rescatar formulario
  @ViewChild('formStudy') formStudy!: NgForm;

  constructor(
    private educationService: EducationService,
    private alertService: AlertService
  ) { }

  //Validar campo
  validateFieldStudy(field: string) {
    return this.formStudy?.controls[field]?.invalid && this.formStudy.controls[field].touched;
  }

  //Añadir formación
  editStudy(id:number) {
    if(this.formStudy.pristine) return;
    this.educationService.editEducation(this.formStudy,id).subscribe((user:User)=>{
      if(user) {
        this.alertService.success("Estudios editados correctamente").then((result)=>{
          this.emitUser.emit(user);
        })
      }
    });
  }

}
