import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';
import { Subject } from '../model/subject.model';


@Component({
  templateUrl: './moodlePage.component.html',
  styleUrls : ['../../assets/css/style.css']
})



export class MoodleComponent implements OnInit {

  subject: Subject;
  courseName : string;
  subjectName : string;
  URL: string;

  constructor(private router: Router, activatedRoute: ActivatedRoute) {
    this.URL = environment.URL;
    this.courseName = activatedRoute.snapshot.params['courseName'];
    this.subjectName = activatedRoute.snapshot.params['subjectName'];
  }

  ngOnInit() {
    
    
  }

}