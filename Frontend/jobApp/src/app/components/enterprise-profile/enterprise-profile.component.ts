import { Component, Input, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';

import { User } from 'src/app/interfaces/interfaces';
import { AlertService } from 'src/app/services/alert.service';
import { AuthService } from 'src/app/services/auth.service';
import { UserService } from 'src/app/services/user.service';
import { environment } from 'src/environments/environment';

@Component({
  selector: 'app-enterprise-profile',
  templateUrl: './enterprise-profile.component.html',
  styleUrls: ['./enterprise-profile.component.css'],
})
export class EnterpriseProfileComponent implements OnInit {

  @Input("user") user!: User;
  @ViewChild("editForm") editForm!: NgForm;
  @ViewChild("photoForm") photoForm!: NgForm;
  showImageError: boolean = false;
  showEditButton: boolean = false;
  selectedFiles?: FileList;
  currentFile?: File;
  baseUrl: string = environment.baseUrl;

  constructor(
    private userService: UserService,
    private authService: AuthService,
    private alertService: AlertService
  ) { }

  ngOnInit(): void {
    //Get user's id
    this.authService.getMyId().subscribe((id: number) => {
      if (this.user.id === id) this.showEditButton = true;
    });
  }

  validateField(field: string): boolean {
    return this.editForm?.controls[field]?.invalid && this.editForm?.controls[field]?.touched;
  }

  editProfile() {
    if (this.editForm.pristine) return;
    else this.userService.editProfile(this.user,this.editForm);
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
        if (file) this.currentFile = file;
        data.append("imageFile", file!);
        this.userService.editImage(data).subscribe((user: User) => {
          if (user) {
            this.alertService.success("Foto editada correctamente");
            this.user = user;
          }
        });
      }
    }
  }

}
