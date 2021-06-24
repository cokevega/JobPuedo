import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';

import { IsAdminGuard } from './guards/is-admin.guard';

const routes: Routes = [
  {
    path:'main',
    loadChildren:()=>import('./main/main.module').then(m=>m.MainModule)
  },
  {
    path: 'offer',
    loadChildren:()=>import("./offer/offer.module").then(m=>m.OfferModule)
  },
  {
    path: 'user',
    loadChildren:()=>import("./user/user.module").then(m=>m.UserModule)
  },
  {
    path: 'auth',
    loadChildren:()=>import("./auth/auth.module").then(m=>m.AuthModule)
  },
  {
    path: 'admin',
    loadChildren:()=>import("./admin/admin.module").then(m=>m.AdminModule),
    canActivate: [IsAdminGuard],
    canLoad: [IsAdminGuard]
  },
  {
    path:'**',
    redirectTo:'main'
  }
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule]
})
export class AppRoutingModule { }
