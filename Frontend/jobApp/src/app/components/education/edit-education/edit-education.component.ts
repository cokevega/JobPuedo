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

  @Input('user') user!:User;
  @Input('study') study!:Education;
  @Output() emitUser=new EventEmitter<User>();
  @ViewChild('formStudy') formStudy!: NgForm;

  constructor(
    private educationService: EducationService,
    private alertService: AlertService
  ) { }

  validateFieldStudy(field: string) {
    return this.formStudy?.controls[field]?.invalid && this.formStudy.controls[field].touched;
  }

  editStudy(id:number) {
    if(this.formStudy.pristine) return;
    this.educationService.editEducation(this.formStudy,id).subscribe((user:User)=>{
      if(user) {
        this.alertService.success("Estudios editados correctamente").then((result)=>{
          this.emitUser.emit(user);
        });
      }
    });
  }

}
