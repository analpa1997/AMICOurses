import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import {User} from '../model/user.model';

@Component({
  styleUrls : ['../../assets/css/profile-update.css'],
  templateUrl: './user-update.component.html'
})
export class UserUpdateComponent {

  userUpdated: boolean;
  user: User;
  private service: UserService;
  private router: Router;

  cancel() {
    window.history.back();
  }

  save(event: any, firstName: string, lastName: string, username: string, mail: string, password: string, country: string, city: string,
       address: string, phone: number, url: string, interests: string) {
    let updateUser: User;
    updateUser = <User>{username: username, password: password, userMail: mail, userFirstName: firstName, userLastName: lastName,
      country: country, city: city, userAddress: address, phoneNumber: phone, urlProfileImage: url, interests: interests};
    this.service.updateUser(updateUser).subscribe(
      user => this.router.navigate(['users/profile']),
      error => console.error('Error updating user: ' + error)
    );
    window.history.back();
  }
}
