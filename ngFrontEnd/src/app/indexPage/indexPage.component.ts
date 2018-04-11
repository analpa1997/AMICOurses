import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';


@Component({
  selector: 'router-outlet',
  templateUrl: './indexPage.component.html',
})



export class IndexPageComponent implements OnInit {

  courses: Course [];
  page : number;
  lastPage : boolean;
  URL: string;

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    this.URL = "https://localhost:8443";
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