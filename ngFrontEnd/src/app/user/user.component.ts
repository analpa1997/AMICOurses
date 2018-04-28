import {Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from './user.service';
import { LoginService } from '../login/login.service';
import {User} from '../model/user.model';
import {Course} from '../model/course.model';
import { environment } from '../../environments/environment';

@Component({
  templateUrl: './user.component.html',
  styleUrls : ['../../assets/css/resume.min.css']
})


export class UserComponent implements OnInit {
  internalName: string;
  user: User;
  image: File;

  private URL;
  constructor(private router: Router, private activatedRoute: ActivatedRoute, public service: UserService, private loginService : LoginService) {
    this.internalName = this.activatedRoute.snapshot.params['internalName'];
    this.URL = environment.URL;
  }

  removeUser() {
    const okResponse = window.confirm('Do you want to remove this user?');
    if (okResponse) {
      this.service.removeUser(this.user).subscribe(
        _ => this.router.navigate(['/admin']),
        error => console.error(error)
      );
    }
  }

  updateUser() {
    this.router.navigate(['/user/' + this.user.internalName + '/update', this.user.userID]);
  }

  ngOnInit() {
    this.service.getImageProfile(this.internalName).subscribe(photo => this.image = photo,
      error => console.log(error));
    this.service.getUser(this.internalName).subscribe(user => {this.user = user, console.log(this.user); },
        error => console.log(error));

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
