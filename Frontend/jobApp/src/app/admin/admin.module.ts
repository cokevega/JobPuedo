import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { AdminRoutingModule } from './admin-routing.module';
import { AdminComponent } from './admin.component';
import { CategoriesComponent } from './categories/categories.component';
import { UsersComponent } from './users/users.component';
import { OffersComponent } from './offers/offers.component';
import { MaterialModule } from '../material/material.module';
import { NgxPaginationModule } from 'ngx-pagination';
import { FormsModule } from '@angular/forms';


@NgModule({
  declarations: [
    AdminComponent,
    CategoriesComponent,
    UsersComponent,
    OffersComponent
  ],
  imports: [
    AdminRoutingModule,
    CommonModule,
    FormsModule,
    MaterialModule,
    NgxPaginationModule
  ]
})
export class AdminModule { }
