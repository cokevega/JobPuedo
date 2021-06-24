import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { AdminComponent } from './admin.component';
import { CategoriesComponent } from './categories/categories.component';
import { OffersComponent } from './offers/offers.component';
import { UsersComponent } from './users/users.component';

const routes: Routes = [
  {
    path: '',
    component: AdminComponent,
    children: [
      {
        path: 'categories',
        component: CategoriesComponent
      },
      {
        path: 'users',
        component: UsersComponent
      },
      {
        path: 'offers',
        component: OffersComponent
      },
      {
        path: '**',
        redirectTo: 'categories'
      }
    ],
  },

];

@NgModule({
  imports: [RouterModule.forChild(routes)],
  exports: [RouterModule]
})
export class AdminRoutingModule { }
