import { CommonModule } from '@angular/common';
import { NgModule } from '@angular/core';

import { ComponentsModule } from '../components/components.module';
import { IndexComponent } from './index/index.component';
import { MainRoutingModule } from './main-routing.module';

import { NgxPaginationModule } from "ngx-pagination";


@NgModule({
  declarations: [
    IndexComponent,
  ],
  imports: [
    CommonModule,
    ComponentsModule,
    MainRoutingModule,
    NgxPaginationModule
  ]
})
export class MainModule { }
