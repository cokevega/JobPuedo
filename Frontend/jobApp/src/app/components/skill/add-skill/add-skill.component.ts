import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { SkillService } from 'src/app/services/skill.service';

@Component({
  selector: 'app-add-skill',
  templateUrl: './add-skill.component.html',
  styleUrls: ['./add-skill.component.css']
})
export class AddSkillComponent {

  @Input('user') user!: User;
  @ViewChild('formSkill') formSkill!: NgForm;
  @Output() emitUser = new EventEmitter<User>();

  constructor(
    private alertService: AlertService,
    private skillService: SkillService
  ) { }

  validateFieldSkill(field: string) {
    return this.formSkill?.controls[field]?.invalid && this.formSkill?.controls[field]?.touched;
  }

  addSkill() {
    this.skillService.addSkill(this.formSkill).subscribe((user: User) => {
      if (user) {
        this.alertService.success("Habilidad añadida con éxito").then((result) => {
          this.emitUser.emit(user);
        });
      }
    })
  }

}
