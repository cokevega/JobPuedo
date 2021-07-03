//Editar perfil usuario trabajador
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { ActivatedRoute, Router } from '@angular/router';

import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';

@Component({
  selector: 'app-edit',
  templateUrl: './edit.component.html',
  styleUrls: ['./edit.component.css']
})
export class EditComponent implements OnInit {

  user!: User;
  @ViewChild("editForm") editForm!: NgForm;
  @ViewChild("photoForm") photoForm!: NgForm;
  showImageError: boolean = false;
  showEditButton: boolean = true;
  selectedFiles?: FileList;
  currentFile?: File;
  dateBorn: string="";

  constructor(
    private authService: AuthService,
    private route: ActivatedRoute,
    private userService: UserService,
    private alertService: AlertService,
    private router:Router,
  ) {

  }

  ngOnInit(): void {
    //User information
    this.userService.findUser(this.route.snapshot.params.id).subscribe((user: User) => {
      if(user) {
        this.user = user;
      }
    });
    //Show edit buttons?
    this.authService.getMyId().subscribe((id: number) => {
      if (this.user && id !== this.user.id) {
        this.showEditButton = false;
      }
    })
  }

  validateField(field: string): boolean {
    return this.editForm?.controls[field]?.invalid && this.editForm?.controls[field]?.touched;
  }

  editProfile() {
    if (this.editForm.pristine) return;
    else {
      this.userService.editProfile(this.user,this.editForm);
    }
  }

  selectFile(event: any) {
    this.selectedFiles = event.target.files;
  }

  editPhoto() {
    if (this.photoForm.invalid) {
      this.showImageError = true;
      return;
    } else {
      let data: FormData = new FormData();
      if (this.selectedFiles) {
        const file: File | null = this.selectedFiles.item(0);
        if (file) {
          this.currentFile = file;
        }
        data.append("imageFile", file!);
        this.userService.editImage(data).subscribe((user: User) => {
          if (user) {
            this.user = user;
            this.alertService.success("Foto editada correctamente");
          }
        });
      }
    }
  }
}
