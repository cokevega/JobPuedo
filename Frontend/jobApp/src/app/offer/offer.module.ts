import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';

import { OfferRoutingModule } from './offer-routing.module';
import { MaterialModule } from '../material/material.module';
import { ComponentsModule } from '../components/components.module';
import { ShowComponent } from './show/show.component';
import { AllComponent } from './all/all.component';

import { EditorModule } from '@tinymce/tinymce-angular';
import { NgxPaginationModule } from 'ngx-pagination';


@NgModule({
  declarations: [
    ShowComponent,
    AllComponent,
  ],
  imports: [
    ComponentsModule,
    CommonModule,
    EditorModule,
    FormsModule,
    MaterialModule,
    NgxPaginationModule,
    OfferRoutingModule,
    ReactiveFormsModule,
  ],
  exports: [
    ShowComponent
  ]
})
export class OfferModule { }
