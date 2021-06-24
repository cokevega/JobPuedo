//Editar experiencia laboral
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Experience, User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { ExperienceService } from 'src/app/services/experience.service';

@Component({
  selector: 'app-edit-experience',
  templateUrl: './edit-experience.component.html',
  styleUrls: ['./edit-experience.component.css']
})
export class EditExperienceComponent {

  //Rescatar formulario
  @ViewChild('formExperience') formExperience!: NgForm;
  //Recibir experiencia
  @Input('experience') experience!: Experience;
  //Recibir usuario
  @Input('user') user!: User;
  //Emitir nuevo usuario
  @Output() emitUser = new EventEmitter<User>();

  constructor(
    private experienceService: ExperienceService,
    private alertService: AlertService
  ) { }

  //Validar campo
  validateFieldExperience(field: string) {
    return this.formExperience?.controls[field]?.invalid && this.formExperience?.controls[field]?.touched;
  }

  //Editar experiencia
  editExperience() {
    if (this.formExperience.pristine) return;
    this.experienceService.editExperience(this.formExperience, this.experience.id).subscribe((user: User) => {
      if (user) {
        this.alertService.success("Experiencia laboral editada correctamente").then((result)=>{
          this.emitUser.emit(user);
        })
      }
    });
  }

}
