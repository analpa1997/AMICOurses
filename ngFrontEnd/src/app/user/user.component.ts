import {Component, Input, OnInit} from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { UserService } from './user.service';
import { LoginService } from '../login/login.service';
import {User} from '../model/user.model';
import {Course} from '../model/course.model';

@Component({
  templateUrl: './user.component.html',
  styleUrls : ['../../assets/css/resume.min.css']
})


export class UserComponent implements OnInit {
  internalName: string;
  private user: User;
  isTheProfileUser: boolean;
  public loginService: LoginService
  constructor(private router: Router, private activatedRoute: ActivatedRoute, public service: UserService,
              ) {

    /*const userInternalName = activatedRoute.snapshot.params['userInternalName'];
    service.getUser(userInternalName).subscribe(
      user => this.user = user,
      error => console.error(error)
    );*/
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
    this.router.navigate(['/user/update', this.user.userID]);
  }
  ngOnInit() {
    this.internalName = this.activatedRoute.snapshot.params['internalName'];
    console.log(this.internalName);
    console.log(this.loginService.user.internalName);

    if (this.internalName === this.loginService.user.internalName) {
      console.log(this.internalName);
      this.isTheProfileUser = true;
      this.user = this.loginService.user;
    } else {
      this.isTheProfileUser = false;
      this.service.getUser(this.internalName).subscribe(user => this.user = user,
        error => console.log(error));
    }
  }
}
