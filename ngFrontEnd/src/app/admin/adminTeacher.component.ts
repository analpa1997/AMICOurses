import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { CourseService } from '../course-information/course.service';
import { User } from '../model/user.model';
import { Subject } from '../model/subject.model';
import {Course} from '../model/course.model';
import {UserService} from '../user/user.service';
import {environment} from '../../environments/environment';

@Component({
  selector: 'admin-teacher-component',
  templateUrl: './adminTeacher.component.html',
  styleUrls : ['../../assets/css/sb-admin.css']
})

export class AdminTeacherComponent {

  constructor(private router: Router, private courseService: CourseService, private userService: UserService){}

  submitForm(name: string, surname: string, username: string, email: string, pass: string){
    let user: User;
    const internalName = username.replace(' ', '-').toLowerCase();

    user = <User>{username: username, password: pass, userMail: email, student: false, internalName: internalName,
                  userFirstName: name, userLastName: surname};
    this.userService.newUser(user).subscribe(
      user => this.router.navigate(['/admin']),
      error => console.error('Error creating new user: ' + error)
    );
  }
}
