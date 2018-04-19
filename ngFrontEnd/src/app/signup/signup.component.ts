import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {UserService } from '../user/user.service';
import {User} from '../model/user.model';

@Component({
  selector:'signup',
  styleUrls : ['../../assets/css/csslogin.css'],
  templateUrl: './signup.component.html'
})
export class SignupComponent {

  newUser: boolean;
  user: User;
  errorSign: boolean;
  repeatPassword: string;
  valError: string;

  constructor(
    private _router: Router,
    activatedRoute: ActivatedRoute,
    private service: UserService) {

    /*const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getUser(id).subscribe(
        user => this.user = user,
        error => console.error(error)
      );
      this.newUser = false;
    } else {
      // this.user = { username: '', userMail: '' };
      this.newUser = true;
    }*/
  }

  save(event: any, username: string, mail: string, pass: string, Rpass: string) {

    event.preventDefault();

    if (this.check(username, mail, pass, Rpass)) {
      this.service.newUser(this.user).subscribe(
        user => {
        },
        error => console.error('Error creating new user: ' + error)
      );
    }
  }

  check(username: string, mail: string, pass: string, Rpass: string) {
    const errors: boolean[] = this.service.checkUser(username, pass, Rpass, mail, true);
    if (errors[0] && errors[1] && errors[2] && errors[3] && errors[4]) {
      this.errorSign = true;
      return true;
    } else {
      this.valError = '';
      if (username = '') {
        this.valError = 'The username can not be empty';
      } else if (pass = '') {
        this.valError = 'The password can not be empty';
      } else if (mail = '') {
        this.valError = 'The userMail can not be empty';
      } else if (!errors[0]) { // Password Match
        if (!(pass === Rpass)) {
          this.valError = 'The passwords are different';
        } else if (pass.length <= 7) {
          this.valError = 'The password is too short';
        } else if (pass.length >= 15) {
          this.valError = 'The password is too long';
      }} else if (!errors[1]) { // Correct Name
        if (username.length <= 4) {
          this.valError = 'The username is too short';
      } else if (username.length >= 16) {
          this.valError = 'The username is too long';
      }} else if (!errors[2]) { // Is Valid Email
        this.valError = 'The email address is not correct';
      } else if (!errors[3]) { // Username in use
        this.valError = 'There is another user with that username';
      } else if (!errors[4]) { // Email in use
        this.valError = 'There is another user with that email address';
      }
      this.errorSign = true;
      return true;
    }
  }
}
