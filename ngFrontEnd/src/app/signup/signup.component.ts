import { Component } from '@angular/core';
import { Router, ActivatedRoute } from '@angular/router';
import {UserService } from '../user/user.service';
import {User} from '../model/user.model';

@Component({
  templateUrl: './signup.component.html',
  styleUrls : ['../../assets/css/login.css']
})
export class SignupComponent {

  newUser: boolean;
  user: User;
  errorSign: boolean;
  repeatPassword: boolean;
  valError: string;

  constructor(
    private _router: Router,
    activatedRoute: ActivatedRoute,
    private service: UserService) {

    const id = activatedRoute.snapshot.params['id'];
    if (id) {
      service.getUser(id).subscribe(
        book => this.user = book,
        error => console.error(error)
      );
      this.newUser = false;
    } else {
      this.user = { username: '', userMail: '' };
      this.newUser = true;
    }
  }

  cancel() {
    window.history.back();
  }

  save() {
    this.service.newUser(this.user).subscribe(
      user => { },
      error => console.error('Error creating new user: ' + error)
    );
    window.history.back();
  }

  check() {
    if (this.service.checkUser(this.user.username, this.user.password, this.repeatPassword, this.user.userMail, false) != null) {
      this.errorSign = false;
    } else {
      this.valError = '';
      if (this.user.username = '') {
        this.valError = 'The username can not be empty';
      } else if (this.user.password = '') {
        this.valError = 'The password can not be empty';
      } else if (this.user.userMail = '') {
        this.valError = 'The userMail can not be empty';
      } else if (!this.service.passwordMatch(this.user.password, this.repeatPassword)) {
        if (!this.user.password === this.repeatPassword) {
          this.valError = 'The passwords are different';
        } else if (this.user.password.length <= 7) {
          this.valError = 'The password is too short';
        } else if (this.user.password.length >= 15) {
          this.valError = 'The password is too long';
      }} else if (!this.service.correctName(this.user.username)) {
        if (this.user.username.length <= 4) {
          this.valError = 'The username is too short';
      } else if (this.user.username.length >= 16) {
          this.valError = 'The username is too long';
        } else if (!this.user.username.match('^[a-zA-Z0-9_-]*$")')) {
          this.valError = 'The username only can contains letters, numbers, - or _.';
      }} else if (!this.service.isValidEmailAddress(this.user.userMail)) {
        this.valError = 'The email address is not correct';
      } else if (userRepository.findByUsername(this.user.username) != null) {
        this.valError = 'There is another user with that username';
      } else if (userRepository.findByUserMail(this.user.userMail) != null) {
        this.valError = 'There is another user with that email address';
      }
      this.errorSign = true;
    }
  }
}
