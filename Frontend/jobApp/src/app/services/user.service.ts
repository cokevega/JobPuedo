import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Params } from '@angular/router';

import { Observable } from 'rxjs';

import { environment } from 'src/environments/environment';
import { Education, Experience, User } from '../interfaces/interfaces';
import { AlertService } from './alert.service';
import { AuthService } from './auth.service';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  baseUrl: string = `${environment.baseUrl}/user`;
  baseUrlAdmin:string=`${environment.baseUrl}/admin/user`;

  constructor(
    private alertService:AlertService,
    private authService:AuthService,
    private http: HttpClient
  ) { }

  //Buscar todos los usuarios
  findAll():Observable<User[]> {
    return this.http.get<User[]>(`${this.baseUrlAdmin}/all`);
  }

  //Buscar usuarios por estatus
  findByStatus(status:number):Observable<User[]> {
    let params:Params={status};
    return this.http.get<User[]>(`${this.baseUrl}/all/status`,{params});
  }

  //Buscar usuario por id
  findUser(id: number):Observable<User> {
    let params:Params={id};
    return this.http.get<User>(`${this.baseUrl}/find`,{params});
  }

  //Editar perfil
  editProfile(user:User,editForm:NgForm) {
    return this.http.put<User>(`${this.baseUrl}/edit`,user).subscribe((user: User) => {
      if (user) {
        if(!editForm.controls['email'].pristine) {
          this.alertService.success("Has cambiado el email: tu sesión se cerrará para que la vuelvas a iniciar con la nueva dirección").then((result)=>{
            this.authService.logout();
          });
        } else this.alertService.success("Tu perfil se ha editado correctamente");
      } 
    });;
  }

  //Editar imagen de perfil
  editImage(form:FormData):Observable<User> {
    return this.http.put<User>(`${this.baseUrl}/edit/image`,form);
  }

  //Obtener el usuario conectado
  whoAmI():Observable<User> {
    return this.http.get<User>(`${this.baseUrl}/me`);
  }

  //Eliminar usuario (soft delete)
  deleteUser(id:number):Observable<boolean> {
    let params:Params={id}
    return this.http.delete<boolean>(`${this.baseUrl}/delete`,{params});
  }

  //Filtrar usuario
  filterUser(form:NgForm):Observable<User[]> {
    return this.http.post<User[]>(`${this.baseUrlAdmin}/filter`,form.value);
  }

  //Eliminar usuario (definitivo)
  deleteUserDefinitely(id:number):Observable<boolean> {
    return this.http.delete<boolean>(`${this.baseUrlAdmin}/delete/${id}`);
  }

  //Hacer administrador a otro usuario
  getNewAdmin(id:number):Observable<User> {
    return this.http.get<User>(`${this.baseUrlAdmin}/admin/${id}`);
  }

  //Ordenar array por fecha de fin
  sortArrayByEnd(component: Education[] | Experience[]) {
    component.sort((a:Education|Experience,b:Education|Experience)=>{
      if(a.end===null && b.end===null) return 0;
      else if(a.end===null) return -1;
      else if(b.end===null) return 1;
      else if(a.end>b.end) return -1;
      else if(a.end<b.end) return 1;
      else return 0;
    });
  }

  //Filtrar trabajadores
  filterWorkers(data:NgForm):Observable<User[]> {
    return this.http.post<User[]>(`${this.baseUrl}/filter`,data.value);
  }

}
