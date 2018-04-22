import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from './login.service';

@Component({
  selector: 'login',
  styleUrls : ['../../assets/css/csslogin.css'],
  templateUrl: './login.component.html'
})
export class LoginComponent {

  constructor(private loginService: LoginService, private router: Router) { }

  logIn(event: any, user: string, pass: string) {

    event.preventDefault();

    this.loginService.logIn(user, pass).subscribe(
      u => {
        console.log(u);
        this.router.navigate(['/index']);
      }, error => alert('Invalid user or password'));
  }

  logOut() {
    this.loginService.logOut().subscribe(
      response => { },
      error => console.log('Error when trying to log out: ' + error)
    );
  }

}
