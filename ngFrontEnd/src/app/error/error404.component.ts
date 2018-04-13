import { Component, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { Course } from '../model/course.model';
import { CourseService } from '../course-information/course.service';
import { environment } from '../../environments/environment';


@Component({
  templateUrl: './error404.component.html',
  styleUrls : ['../../assets/css/404Error.css']
})



export class Error404  {

  constructor(private router: Router, private courseService: CourseService, activatedRoute: ActivatedRoute) {
  }

}