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
  constructor(private router: Router, private activatedRoute: ActivatedRoute, public service: UserService, private loginService : LoginService) {
    this.internalName = this.activatedRoute.snapshot.params['internalName'];
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
    console.log(this.internalName);
      this.service.getUser(this.internalName).subscribe(user => this.user = user,
        error => console.log(error));
    
  }
}
