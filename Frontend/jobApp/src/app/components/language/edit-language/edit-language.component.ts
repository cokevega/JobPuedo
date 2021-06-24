//Editar idioma
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { Language, User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-edit-language',
  templateUrl: './edit-language.component.html',
  styleUrls: ['./edit-language.component.css']
})
export class EditLanguageComponent {

  //Recibir idioma
  @Input('language') language!:Language;
  //Recibir usuario
  @Input('user') user!:User;
  //Emitir nuevo usuario
  @Output() emitUser=new EventEmitter<User>();
  //Rescatar formulario
  @ViewChild('formLanguage') formLanguage!: NgForm;

  constructor(
    private alertService:AlertService,
    private languageService:LanguageService
  ) { }

  //Validar campo
  validateFieldLanguage(field: string):boolean {
    return this.formLanguage?.controls[field]?.invalid && this.formLanguage?.controls[field]?.touched;
  }

  //Editar idioma
  editLanguage(id:number) {
    if(this.formLanguage.pristine) return;
    this.languageService.editLanguage(this.formLanguage,id).subscribe((user: User) => {
      if (user) {
        this.alertService.success("Idioma editado con éxito").then((result)=>{
          this.emitUser.emit(user);
        })
      }
    })
  }

}
