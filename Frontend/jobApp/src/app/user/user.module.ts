import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';

import { NgxPaginationModule } from 'ngx-pagination';

import { UserRoutingModule } from './user-routing.module';
import { ShowComponent } from './show/show.component';
import { AllComponent } from './all/all.component';
import { ComponentsModule } from '../components/components.module';
import { EditComponent } from './edit/edit.component';


@NgModule({
  declarations: [
    AllComponent,
    EditComponent,
    ShowComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    FormsModule,
    NgxPaginationModule,
    UserRoutingModule,
  ]
})
export class UserModule { }
