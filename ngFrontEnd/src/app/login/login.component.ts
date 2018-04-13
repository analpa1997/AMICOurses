import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Component({
  styleUrls : ['../../assets/css/csslogin.css'],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  constructor(private loginService: LoginService) { }

  logIn(event: any, user: string, pass: string) {

    event.preventDefault();

    this.loginService.logIn(user, pass).subscribe(
      u => {console.log(u)},
      error => alert('Invalid user or password ')
    );
  }

}
