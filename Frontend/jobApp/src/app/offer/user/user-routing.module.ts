import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ApplyComponent } from './apply/apply.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'apply/:id',
        component: ApplyComponent
      },
      {
        path: '**',
        redirectTo: '/offer/all'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
