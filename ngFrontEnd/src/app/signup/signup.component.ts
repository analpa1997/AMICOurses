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

  user: User;
  errorSign: boolean;
  valError: string;

  constructor(
    private _router: Router,
    activatedRoute: ActivatedRoute,
    private service: UserService) {}

  checkUM(event: any, username: string, mail: string, pass: string, Rpass: string) {
    event.preventDefault();
    this.service.checkUsernameAndMail(username, mail).subscribe(
      response => this.save(response[0], response[1], username, mail, pass, Rpass),
          error => console.error('Error creating new user: ' + error));
  }
  save(userB: boolean, mailB: boolean, username: string, mail: string, pass: string, Rpass: string) {
    console.log(userB);
    console.log(mailB);
    console.log(username);
    console.log(mail);
    console.log(pass);
    console.log(Rpass);
    if (!userB) {// Username in use
      this.errorSign = true;
      this.valError = 'There is another user with that username';
    } else if (!mailB) { // Email in use
      this.errorSign = true;
      this.valError = 'There is another user with that email address';
    } else if (this.check(username, mail, pass, Rpass)) {
      let newUser: User;
      const internalName = username.replace(' ', '-').toLowerCase();
      newUser = <User>{username: username, password: pass, userMail: mail, student: true, internalName: internalName};
      this.service.newUser(newUser).subscribe(
        user => this._router.navigate(['/login']),
        error => console.error('Error creating new user: ' + error)
      );
    }
  }

  check(username: string, mail: string, pass: string, Rpass: string) {
    const errors: boolean[] = this.service.checkUser(username, pass, Rpass, mail, true);
    if (errors[0] && errors[1] && errors[2]) {
      this.errorSign = false;
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
        } else if (pass.length > 7) {
          this.valError = 'The password is too short';
        } else if (pass.length < 15) {
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
