import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { NgxPaginationModule } from "ngx-pagination";
import { EditorModule } from '@tinymce/tinymce-angular';

import { EnterpriseRoutingModule } from './enterprise-routing.module';
import { CreateComponent } from './create/create.component';
import { MaterialModule } from 'src/app/material/material.module';
import { EditComponent } from './edit/edit.component';
import { ApplicationsComponent } from './applications/applications.component';
import { ComponentsModule } from 'src/app/components/components.module';

@NgModule({
  declarations: [
    CreateComponent,
    EditComponent,
    ApplicationsComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    EditorModule,
    EnterpriseRoutingModule,
    FormsModule,
    MaterialModule,
    NgxPaginationModule,
    ReactiveFormsModule
  ]
})
export class EnterpriseModule { }
