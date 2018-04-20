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

  checkUM(event: any, username: string, mail: string, pass: string, Rpass: string) {
    event.preventDefault();
    this.service.checkUsernameAndMail(username, mail).subscribe(
      response => this.save(response[0], response[1], username, mail, pass, Rpass),
          error => console.error('Error creating new user: ' + error));
  }
  save(userB: boolean, mailB: boolean, username: string, mail: string, pass: string, Rpass: string) {
    if (!userB) {// Username in use
      this.valError = 'There is another user with that username';
    } else if (!mailB) { // Email in use
      this.valError = 'There is another user with that email address';
    } else if (this.check(username, mail, pass, Rpass)) {
      this.user = <User>{username: username, password: pass, userMail: mail, student: true};
      this.service.newUser(this.user).subscribe(
        user => {
        },
        error => console.error('Error creating new user: ' + error)
      );
    }
  }

  check(username: string, mail: string, pass: string, Rpass: string) {
    const errors: boolean[] = this.service.checkUser(username, pass, Rpass, mail, true);
    if (errors[0] && errors[1] && errors[2]) {
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
      }
      this.errorSign = true;
      return true;
    }
  }
}
