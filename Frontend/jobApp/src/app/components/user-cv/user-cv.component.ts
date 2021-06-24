//Mostrar perfil/CV de usuario trabajador y editar skills, idiomas, experiencias y formación
import { Component, Input, OnInit, DoCheck } from '@angular/core';

import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { AuthService } from 'src/app/services/auth.service';
import { EducationService } from 'src/app/services/education.service';
import { ExperienceService } from 'src/app/services/experience.service';
import { LanguageService } from 'src/app/services/language.service';
import { SkillService } from 'src/app/services/skill.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-user-cv',
  templateUrl: './user-cv.component.html',
  styleUrls: ['./user-cv.component.css'],
})
export class UserCvComponent implements OnInit, DoCheck {

  //Recibir usuario
  @Input('user') user!: User;
  bornDate!: string;
  showEditButton: boolean = false;
  id!: number;
  baseUrl: string = environment.baseUrl;

  constructor(
    private alertService: AlertService,
    private authService: AuthService,
    private educationService: EducationService,
    private experienceService: ExperienceService,
    private languageService: LanguageService,
    private skillService: SkillService,
    private userService:UserService
  ) { }

  ngOnInit(): void {
    //Obtener id
    this.authService.getMyId().subscribe((id: number) => {
      if (id) this.id = id;
    });
  }

  ngDoCheck(): void {
    //Habilitar los botones de edición si es el usuario dueño del perfil
    if (this.id) {
      if (this.user?.id === this.id) this.showEditButton = true;
      this.bornDate = this.user?.born?.toString().substring(0, 10)!;
    }
    if (this.user && this.user.education) this.userService.sortArrayByEnd(this.user.education);
    if (this.user && this.user.experiences) this.userService.sortArrayByEnd(this.user.experiences);
  }

  //Recargar la página cuando se recibe una actualización (usuario emitido)
  userEmitted(user: User) {
    window.location.reload();
  }

  //Eliminar experiencia
  deleteExperience(id: number) {
    this.alertService.confirmAction(
      'Se eliminará esta experiencia de tu CV y esta acción no podrá deshacerse',
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.experienceService.deleteExperience(id).subscribe((user: User) => {
          if (user) {
            this.user = user;
            this.alertService.success("Se ha eliminado la experiencia correctamente");
          }
        });
      }
    });
  }

  //Eliminar idioma
  deleteLanguage(id: number) {
    this.alertService.confirmAction(
      "Se eliminará este idioma y esta acción no podrá deshacerse",
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.languageService.deleteLanguage(id).subscribe((user: User) => {
          if (user) {
            this.user = user;
            this.alertService.success("Se ha eliminado el idioma correctamente");
          }
        });
      }
    });
  }

  //Eliminar formación
  deleteStudy(id: number) {
    this.alertService.confirmAction(
      'Se eliminarán estos estudios y esta acción no podrá deshacerse',
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.educationService.deleteEducation(id).subscribe((user: User) => {
          if (user) {
            this.user = user;
            this.alertService.success("Estudios eliminados correctamente");
          }
        });
      }
    });
  }

  //Eliminar skill
  deleteSkill(id: number) {
    this.alertService.confirmAction(
      'Se eliminará esta skill y esta acción no podrá deshacerse',
      'Sí, eliminar',
      environment.blueButton,
      environment.redButton
    ).then((result) => {
      if (result.isConfirmed) {
        this.skillService.deleteSkill(id).subscribe((user: User) => {
          if (user) {
            this.user = user;
            this.alertService.success("Se ha eliminado la skill correctamente");
          }
        });
      }
    });
  }

}