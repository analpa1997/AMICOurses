import { Component} from '@angular/core';
import { Router } from '@angular/router';
import { LoginService } from '../login/login.service';


@Component({
  selector: 'admin-header-component',
  templateUrl: './adminHeader.component.html',
  styleUrls : ['../../assets/css/sb-admin.css']
})



export class AdminHeaderComponent {

  constructor(private loginService : LoginService, private router: Router){
  }

  logOut() {
    this.loginService.logOut().subscribe(
      response => {
        this.router.navigate(['']);
      },
      error => console.log('Error when trying to log out: ' + error)
    );
  }
}
