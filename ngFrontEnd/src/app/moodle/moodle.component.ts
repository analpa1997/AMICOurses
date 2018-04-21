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
import { MoodleEvaluationComponent } from './moodleEvaluation.component';
import { MoodleProgressComponent } from './moodleProgress.component';
import { User } from '../model/user.model';


@Component({
  templateUrl: './moodle.component.html',
  styleUrls: ['../../assets/css/student-subject.css']
})



export class MoodleComponent implements AfterViewInit {


  subject: Subject;
  courseName: string;
  subjectName: string;
  URL: string;
  teachersPanel: string;

  listUsers : User [];


  @ViewChild(MoodleContentsComponent)  contentsTab: MoodleContentsComponent;
  @ViewChild(MoodleEvaluationComponent)  evaluationTab: MoodleEvaluationComponent;
  @ViewChild(MoodleProgressComponent) progressTab: MoodleProgressComponent;

  constructor(private router: Router, activatedRoute: ActivatedRoute, private loginService: LoginService, private moodleService: MoodleService) {
    this.URL = environment.URL;
    this.courseName  =  activatedRoute.snapshot.params['courseName'];
    this.subjectName = activatedRoute.snapshot.params['subjectName'];
    this.teachersPanel = "";
  }

  ngAfterViewInit() {
    this.generatePage();
  }

  generatePage() {
    this.moodleService.getSubject(this.courseName, this.subjectName).subscribe(
      response => {
        this.teachersPanel = this.loginService.isStudent ? "Teachers" : "Students";
        this.subject = response;
        /* Retrieve the contents */
        this.contentsTab.generateContent(this.subject.numberModules);
        this.generateListUsers();
      },
      error => {
        this.moodleService.errorHandler(error);
      },
    );
  }

  generateListUsers () {
    this.listUsers = [];
    if (this.loginService.isStudent){
      this.subject.teachers.forEach(teacher => {
        if (!teacher.roles.includes("ROLE_ADMIN")){
          this.listUsers.push(teacher);
        }
      });
    } else {
      this.subject.users.forEach(student => {
        if (student.student){
          this.listUsers.push(student);
        }
      });
    }
  }

  getContents($event) {

    switch ($event.nextId) {
      case "contents": {
        this.contentsTab.generateContent(this.subject.numberModules);
        break;
      }
      case "evaluation": {
        this.evaluationTab.getPractices();
        break;
      }
      case "progress": {
          this.progressTab.getChartData();
        break;
      }

    }
  }
}