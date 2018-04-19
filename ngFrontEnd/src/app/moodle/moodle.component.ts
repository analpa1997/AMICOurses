import { Component, OnInit, ViewChild, ElementRef, AfterViewInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';
import { MoodleService } from './moodle.service';
import { LoginService } from '../login/login.service';
import { MoodleContentsComponent } from './moodleContents.component';
import { Studyitem } from '../model/studyitem.model';


@Component({
  templateUrl: './moodle.component.html',
  styleUrls : ['../../assets/css/student-subject.css']
})



export class MoodleComponent implements AfterViewInit  {


  subject: Subject;
  courseName : string;
  subjectName : string;
  URL: string;
  teachersPanel : string;


   @ViewChild(MoodleContentsComponent) contentsTab: MoodleContentsComponent;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService : MoodleService) {
    this.URL = environment.URL;
    this.courseName = activatedRoute.snapshot.params['courseName'];
    this.subjectName = activatedRoute.snapshot.params['subjectName'];
    this.teachersPanel = "";
  }

  ngAfterViewInit() {
    this.moodleService.getSubject(this.courseName, this.subjectName).subscribe(
      response => {
        this.teachersPanel = this.loginService.isStudent ? "Teachers" : "Students";
        this.subject = response;
        /* Retrieve the contents */
        this.contentsTab.generateContent(this.subject.numberModules);
      },
      error => {
        this.moodleService.errorHandler(error);
      },
    );
  
  }

  getContents($event){
    this.contentsTab.generateContent(this.subject.numberModules);
  }

  

}