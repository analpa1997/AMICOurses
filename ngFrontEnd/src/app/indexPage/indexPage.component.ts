import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';


@Component({
  templateUrl: './indexPage.component.html',
  styleUrls : ['../../assets/css/style.css']
})



export class IndexPageComponent implements OnInit {

  courses: Course [];
  page : number;
  lastPage : boolean;
  URL: string;

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    this.URL = environment.URL;
  }

  ngOnInit() {
    this.courseService.getCourses(undefined, undefined).subscribe(
      response => {
        this.courses = response["content"];
        this.page = response["number"];
        this.lastPage = response["last"];
      },
      error => console.log(error),
    );
    
  }

}