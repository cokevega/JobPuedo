import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { ExperienceService } from 'src/app/services/experience.service';

@Component({
  selector: 'app-add',
  templateUrl: './add.component.html',
  styleUrls: ['./add.component.css']
})
export class AddComponent {

  @ViewChild('formExperience') formExperience!: NgForm;
  @Input('user') user!: User;
  @Output() emitUser=new EventEmitter<User>();

  constructor(
    private experienceService:ExperienceService,
    private alertService:AlertService
  ) { }

  validateFieldExperience(field: string) {
    return this.formExperience?.controls[field]?.invalid && this.formExperience?.controls[field]?.touched;
  }

  addExperience() {
    this.experienceService.addExperience(this.formExperience).subscribe((user: User) => {
      if (user) {
        this.alertService.success("Experiencia laboral agregada satisfactoriamente").then((result)=>{
          this.emitUser.emit(user);
        })
      }
    });
  }

}
