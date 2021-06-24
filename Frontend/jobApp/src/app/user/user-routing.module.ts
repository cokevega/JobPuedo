import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { IdentityGuard } from '../guards/identity.guard';
import { IsEnterpriseGuard } from '../guards/is-enterprise.guard';
import { ViewProfileGuard } from '../guards/view-profile.guard';
import { AllComponent } from './all/all.component';
import { EditComponent } from './edit/edit.component';
import { ShowComponent } from './show/show.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'all',
        component: AllComponent,
        canActivate: [IsEnterpriseGuard],
        canLoad: [IsEnterpriseGuard]
      },
      {
        path: 'show/:id',
        component: ShowComponent,
        canActivate: [ViewProfileGuard]
      },
      {
        path: 'edit/:id',
        component: EditComponent,
        canActivate: [IdentityGuard],
      },
      {
        path: '**',
        redirectTo: '/main/index'
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class UserRoutingModule { }
