import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';
import { LoginService } from '../login/login.service';
import { Studyitem } from '../model/studyitem.model';
import { User } from '../model/user.model';
import { SubjectListService } from './subjectList.service';


@Component({
  templateUrl: './subjectList.component.html',
  styleUrls: ['../../assets/css/course-overview.css']
})



export class SubjectListComponent implements OnInit {


  subjects: Subject [];
  course : Course;
  courseName: string;
  
  URL: string;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private subjectListService : SubjectListService) {
    this.URL = environment.URL;
    this.courseName  =  activatedRoute.snapshot.params['courseName'];
  }

  ngOnInit() {
    this.generatePage();
  }

  generatePage() {
      this.subjectListService.getCourse(this.courseName).subscribe(
          res => {this.course = res; console.log(this.course)},
          error => this.subjectListService.errorHandler(error),
      );
  }

  
}