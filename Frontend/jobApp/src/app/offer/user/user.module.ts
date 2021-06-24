import { NgModule } from '@angular/core'
import { CommonModule } from '@angular/common';

import { UserRoutingModule } from './user-routing.module';
import { ApplyComponent } from './apply/apply.component';
import { OfferModule } from '../offer.module';


@NgModule({
  declarations: [
    ApplyComponent,
  ],
  imports: [
    CommonModule,
    UserRoutingModule,
    OfferModule
  ]
})
export class UserModule { }
