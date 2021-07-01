import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Skill, User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { SkillService } from 'src/app/services/skill.service';

@Component({
  selector: 'app-edit-skill',
  templateUrl: './edit-skill.component.html',
  styleUrls: ['./edit-skill.component.css']
})
export class EditSkillComponent {

  @Input('skill') skill!:Skill;
  @Input('user') user!:User;
  @Output() emitUser=new EventEmitter<User>();
  @ViewChild('formSkill') formSkill!: NgForm;

  constructor(
    private alertService:AlertService,
    private skillService:SkillService
  ) { }

  validateFieldSkill(field: string):boolean {
    return this.formSkill?.controls[field]?.invalid && this.formSkill?.controls[field]?.touched;
  }

  editSkill(id:number) {
    if(this.formSkill.pristine) return;
    this.skillService.editSkill(this.formSkill,id).subscribe((user: User) => {
      if (user) {
        this.alertService.success("Habilidad editada con éxito").then((result)=>{
          this.emitUser.emit(user);
        });
      }
    });
  }

}
