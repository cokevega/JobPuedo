//Añadir idioma
import { Component, EventEmitter, Input, Output, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { LanguageService } from 'src/app/services/language.service';

@Component({
  selector: 'app-add-language',
  templateUrl: './add-language.component.html',
  styleUrls: ['./add-language.component.css']
})
export class AddLanguageComponent {

  //Recibir usuario
  @Input('user') user!:User;
  //Rescatar formulario
  @ViewChild('formLanguage') formLanguage!: NgForm;
  //Emitir nuevo usuario
  @Output() emitUser=new EventEmitter<User>();

  constructor(
    private alertService:AlertService,
    private languageService:LanguageService
  ) { }
  
  //Validar campo
  validateFieldLanguage(field: string) {
    return this.formLanguage?.controls[field]?.invalid && this.formLanguage?.controls[field]?.touched;
  }

  //Añadir idioma
  addLanguage() {
    this.languageService.addLanguage(this.formLanguage).subscribe((user: User) => {
      if (user) {
        this.alertService.success("Idioma agregado con éxito").then((result)=>{
          this.emitUser.emit(user);
        })
      }
    })
  }

}
