import { Component, OnInit } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import { User } from '../model/user.model';
import { LoginService } from '../login/login.service';

@Component({
  styleUrls: ['../../assets/css/profile-update.css'],
  templateUrl: './user-update.component.html'
})
export class UserUpdateComponent implements OnInit {

  userUpdated: boolean;
  internalName: string;
  user: User;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private service: UserService, private loginService: LoginService) {
    this.internalName = this.activatedRoute.snapshot.params['internalName'];
  }
  cancel() {
    window.history.back();
  }

  save(event: any, firstName: string, lastName: string, username: string, mail: string, password: string, country: string, city: string,
    address: string, phone: string, profileImage: any, interests: string) {
    let updateUser: User;
    updateUser = <User>{
      userID: this.user.userID, username: username, password: password, userMail: mail, userFirstName: firstName, userLastName: lastName,
      country: country, city: city, userAddress: address, phoneNumber: phone, interests: interests, internalName: this.user.internalName
    };


    this.service.updateUser(updateUser).subscribe(
      user => {
        this.user = user;
        this.loginService.user = user;
        if (profileImage.files[0] != null) {
          this.service.uploadImage(this.user.internalName, profileImage.files[0]).subscribe(
            user => {
            this.user = <User>user;
              this.router.navigate(['users/', this.user.internalName, 'profile']);
            },
            error => console.error('Error updating user image: ' + error)
          );
        } else {
          this.router.navigate(['users/', this.user.internalName, 'profile']);
        }
      },
      error => console.error('Error updating user: ' + error)
    );
  }
  ngOnInit() {
    this.service.getUser(this.internalName).subscribe(user => {
      this.user = user,
        console.log(user);
    },
      error => console.log(error));
  }
}
