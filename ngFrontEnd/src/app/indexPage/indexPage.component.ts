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
  page: number;
  name: string;
  type: string;
  allTypes: string [];
  sort: string;
  lastPage: boolean;
  URL: string;
  emptyContent: boolean;

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
    this.URL = environment.URL;
  }

  initTypes() {
    this.courseService.getTypes().subscribe(
      response => {
        this.allTypes = response;
        console.log(this.allTypes);
      },
      error => console.log(error),
    );
  }

  ngOnInit() {
    this.courseService.getCourses(undefined, undefined, undefined, undefined).subscribe(
      response => {
        this.courses = response['content'];
        this.page = response['number'];
        this.lastPage = response['last'];
        this.name = '';
        this.type = 'all';
        this.sort = 'courseID';
        this.emptyContent = this.courses.length === 0;
        this.allTypes = new Array();
        this.initTypes();
        console.log(this.allTypes);
      },
      error => console.log(error),
    );

  }



  byNewest() {
    this.courseService.getCourses(0, this.name, this.type, 'startDate').subscribe(
      response => {
        this.courses = response['content'];
        this.page = response['number'];
        this.lastPage = response['last'];
        this.sort = 'startDate';
        this.emptyContent = this.courses.length === 0;
      },
      error => console.log(error),
    );
  }

  byTrend() {
    this.courseService.getCourses(0, this.name, this.type, 'numberOfUsers').subscribe(
      response => {
        this.courses = response['content'];
        this.page = response['number'];
        this.lastPage = response['last'];
        this.sort = 'numberOfUsers';
        this.emptyContent = this.courses.length === 0;
      },
      error => console.log(error),
    );
  }

  byType() {
    this.courseService.getCourses(0, this.name, this.type, 'numberOfUsers').subscribe(
      response => {
        this.courses = response['content'];
        this.page = response['number'];
        this.lastPage = response['last'];
        this.sort = 'numberOfUsers';
        this.emptyContent = this.courses.length === 0;
      },
      error => console.log(error),
    );
  }

  showMoreCourses() {
    this.courseService.getCourses((this.page + 1), this.name, this.type, this.sort).subscribe(
      response => {
        this.emptyContent = this.courses.length === 0;
        this.courses.concat(response['content']);
        this.page = response['number'];
        this.lastPage = response['last'];

      },
      error => console.log(error),
    );

  }
}
