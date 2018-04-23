import {Component, OnInit} from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import { UserService } from './user.service';
import {User} from '../model/user.model';
import { LoginService } from '../login/login.service';

@Component({
  styleUrls : ['../../assets/css/profile-update.css'],
  templateUrl: './user-update.component.html'
})
export class UserUpdateComponent implements OnInit{

  userUpdated: boolean;
  internalName: string;
  user: User;
  private service: UserService;
  private router: Router;
  private activatedRoute: ActivatedRoute;
  private loginService: LoginService;

  cancel() {
    window.history.back();
  }

  save(event: any, firstName: string, lastName: string, username: string, mail: string, password: string, country: string, city: string,
       address: string, phone: number, url: string, interests: string) {
    let updateUser: User;
    updateUser = <User>{username: username, password: password, userMail: mail, userFirstName: firstName, userLastName: lastName,
      country: country, city: city, userAddress: address, phoneNumber: phone, urlProfileImage: url, interests: interests};
    this.service.updateUser(updateUser).subscribe(
      user => {
        this.user = user,
        this.router.navigate(['users/profile']); },
          error => console.error('Error updating user: ' + error)
    );
    window.history.back();
  }
  ngOnInit() {
    this.internalName = this.activatedRoute.snapshot.params['internalName'];
    console.log(this.internalName);
    this.service.getUser(this.internalName).subscribe(user => this.user = user,
      error => console.log(error));
  }
}
