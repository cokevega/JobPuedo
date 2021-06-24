import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { MyOfferGuard } from 'src/app/guards/my-offer.guard';
import { ApplicationsComponent } from './applications/applications.component';
import { CreateComponent } from './create/create.component';
import { EditComponent } from './edit/edit.component';

const routes: Routes = [
  {
    path: '',
    children:[
      {
        path: 'create',
        component: CreateComponent,
      },
      {
        path: 'edit/:id',
        component: EditComponent,
        canActivate: [MyOfferGuard]
      },
      {
        path:'applications/:id',
        component: ApplicationsComponent,
        canActivate: [MyOfferGuard]
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
export class EnterpriseRoutingModule { }
