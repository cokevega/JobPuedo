//Editar skill
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

  //Recibir skill
  @Input('skill') skill!:Skill;
  //Recibir usuario
  @Input('user') user!:User;
  //Emitir nuevo usuario
  @Output() emitUser=new EventEmitter<User>();
  //Rescatar formulario
  @ViewChild('formSkill') formSkill!: NgForm;

  constructor(
    private alertService:AlertService,
    private skillService:SkillService
  ) { }

  //Validar campo
  validateFieldSkill(field: string):boolean {
    return this.formSkill?.controls[field]?.invalid && this.formSkill?.controls[field]?.touched;
  }

  //Editar skill
  editSkill(id:number) {
    if(this.formSkill.pristine) return;
    this.skillService.editSkill(this.formSkill,id).subscribe((user: User) => {
      if (user) {
        this.alertService.success("Habilidad editada con éxito").then((result)=>{
          this.emitUser.emit(user);
        });
      }
    })
  }

}
