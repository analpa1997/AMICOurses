import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';
import { MoodleService } from './moodle.service';
import { LoginService } from '../login/login.service';


@Component({
  templateUrl: './moodlePage.component.html',
  styleUrls : ['../../assets/css/student-subject.css']
})



export class MoodleComponent implements OnInit {

  subject: Subject;
  courseName : string;
  subjectName : string;
  URL: string;
  teachers : string;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService : MoodleService) {
    this.URL = environment.URL;
    this.courseName = activatedRoute.snapshot.params['courseName'];
    this.subjectName = activatedRoute.snapshot.params['subjectName'];
    this.teachers = "";
  }

  ngOnInit() {
    this.moodleService.getSubject(this.courseName, this.subjectName).subscribe(
      response => {
        console.log(response);
        this.teachers = this.loginService.isStudent ? "Teachers" : "Students";
        this.subject = response;
      },
      error => {
        console.log("Error " + error.status);
        if (error.status==401) {
          this.router.navigate(['/login']); //Forbidden
        }

        if (error.status == 500) {
          this.router.navigate(['/error404']); //Must be a 500 error
        }

        this.router.navigate(['/error404']);

      },
    );
  
  }

}