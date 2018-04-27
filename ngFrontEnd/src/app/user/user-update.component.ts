import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import {User} from '../model/user.model';
import { LoginService } from '../login/login.service';

@Component({
  styleUrls : ['../../assets/css/profile-update.css'],
  templateUrl: './user-update.component.html'
})
export class UserUpdateComponent implements OnInit {

  userUpdated: boolean;
  internalName: string;
  user: User;
  private loginService: LoginService;

  constructor(private router: Router, private activatedRoute: ActivatedRoute, private service: UserService) {
    this.internalName = this.activatedRoute.snapshot.params['internalName'];
  }
  cancel() {
    window.history.back();
  }

  save(event: any, firstName: string, lastName: string, username: string, mail: string, password: string, country: string, city: string,
       address: string, phone: string, url: string, interests: string) {
    let updateUser: User;
    updateUser = <User>{username: username, password: password, userMail: mail, userFirstName: firstName, userLastName: lastName,
      country: country, city: city, userAddress: address, phoneNumber: phone, urlProfileImage: url, interests: interests, internalName : this.user.internalName};
    this.service.updateUser(updateUser).subscribe(
      user => {
        this.user = user,

        this.router.navigate(['users/' + this.user.internalName + '/profile']); },
          error => console.error('Error updating user: ' + error)
    );
  }
  ngOnInit() {
    this.service.getUser(this.internalName).subscribe(user => {this.user = user,
    console.log(user); },
      error => console.log(error));
  }
}
