import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { RouterModule } from '@angular/router';

import { OfferCardComponent } from './offer-card/offer-card.component';
import { MaterialModule } from 'src/app/material/material.module';
import { SearchFormComponent } from './search-form/search-form.component';
import { UserCvComponent } from './user-cv/user-cv.component';
import { EnterpriseProfileComponent } from './enterprise-profile/enterprise-profile.component';
import { AddComponent } from './experience/add/add.component';
import { AddLanguageComponent } from './language/add-language/add-language.component';
import { AddEducationComponent } from './education/add-education/add-education.component';
import { EditEducationComponent } from './education/edit-education/edit-education.component';
import { EditExperienceComponent } from './experience/edit-experience/edit-experience.component';
import { EditLanguageComponent } from './language/edit-language/edit-language.component';
import { AddSkillComponent } from './skill/add-skill/add-skill.component';
import { EditSkillComponent } from './skill/edit-skill/edit-skill.component';
import { DeleteAccountButtonComponent } from './delete-account-button/delete-account-button.component';

@NgModule({
  declarations: [
    AddComponent,
    AddLanguageComponent,
    AddEducationComponent,
    EditEducationComponent,
    EditExperienceComponent,
    EditLanguageComponent,
    EnterpriseProfileComponent,
    OfferCardComponent,
    SearchFormComponent,
    UserCvComponent,
    AddSkillComponent,
    EditSkillComponent,
    DeleteAccountButtonComponent,
  ],
  imports: [
    CommonModule,
    FormsModule,
    MaterialModule,
    RouterModule
  ],
  exports: [
    EnterpriseProfileComponent,
    OfferCardComponent,
    SearchFormComponent,
    UserCvComponent,
    DeleteAccountButtonComponent
  ]
})
export class ComponentsModule { }
