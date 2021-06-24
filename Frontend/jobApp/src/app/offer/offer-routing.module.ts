import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { IsEnterpriseUserGuard } from '../guards/is-enterprise-user.guard';
import { IsEnterpriseGuard } from '../guards/is-enterprise.guard';
import { IsUserGuard } from '../guards/is-user.guard';
import { AllComponent } from './all/all.component';
import { ShowComponent } from './show/show.component';

const routes: Routes = [
  {
    path: '',
    children: [
      {
        path: 'show/:id',
        component: ShowComponent
      },
      {
        path: 'all',
        component: AllComponent,
        canActivate: [IsEnterpriseUserGuard],
        canLoad: [IsEnterpriseUserGuard]
      },
      {
        path: 'enterprise',
        loadChildren: ()=>import('./enterprise/enterprise.module').then(m=>m.EnterpriseModule),
        canActivate: [IsEnterpriseGuard],
        canLoad: [IsEnterpriseGuard]
      },
      {
        path: 'user',
        loadChildren: ()=>import('./user/user.module').then(m=>m.UserModule),
        canActivate: [IsUserGuard],
        canLoad: [IsUserGuard]
      }
    ]
  }
];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class OfferRoutingModule { }
